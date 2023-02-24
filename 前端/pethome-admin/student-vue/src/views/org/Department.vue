<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.keyword" placeholder="姓名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="selectDepartment">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="departments" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="name" label="部门名称" width="160" sortable>
			</el-table-column>
      <el-table-column prop="sn" label="部门编号" width="160" sortable>
      </el-table-column>
			<el-table-column prop="state" label="状态" width="180" :formatter="formatState" sortable>
        <template scope="scope">
          <span v-if="scope.row.state == 0" style="color: #11b95c">正常</span>
          <span v-else-if="scope.row.state == -1" style="color: #e64242">禁用</span>
          <span v-else style="color: #5e7382">未知</span>
        </template>
      </el-table-column>
			<el-table-column prop="manager.username" label="管理员" width="160" sortable>
			</el-table-column>
			<el-table-column prop="parent.name" label="上级部门" width="160" sortable>
			</el-table-column>
			<el-table-column label="操作" width="160">
				<template scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange"
                     :page-size="pageSize"
                     :total="total"
                     :current-page="currentPage"
                     style="float:right;">
			</el-pagination>
		</el-col>

		<!--新增和编辑界面-->
    <!--  title :dialog标题
          :visible.sync： 控制dialog窗口的时候打开
          :close-on-click-modal :控制右上角的x
          :model 双向绑定表单数据
          :rules 表示校验规则
          ref 表单别名 -->
    <!--  点击新增/编辑=>开启模态框=>输入数据=>然后克隆一份赋值给绑定的Form对象=>点击提交表单向后端发送请求=>更新成功  -->
		<el-dialog :title="dialogTitle" :visible.sync="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="部门名称" prop="name">
					<el-input v-model="editForm.name" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="部门编号" prop="sn">
          <el-input v-model="editForm.sn" auto-complete="off"></el-input>
        </el-form-item>
				<el-form-item label="状态">
					<el-radio-group v-model="editForm.state">
						<el-radio class="radio" :label="0">启用</el-radio>
						<el-radio class="radio" :label="-1">禁用</el-radio>
					</el-radio-group>
				</el-form-item>
        <!--   :key:主键
               :label 展示的信息
               :value 赋值给v-model里的对象-->
				<el-form-item label="管理员">
          <el-select v-model="editForm.manager"  value-key="id" placeholder="请选择">
            <el-option
                v-for="item in employees"
                :key="item.id"
                :label="item.username"
                :value="item">
              <span style="float: left">{{ item.username }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.phone }}</span>
            </el-option>
          </el-select>
				</el-form-item>
        <el-form-item label="上级部门">
          <el-cascader v-model="editForm.parentDir"
              :options="deptTree"
              :props="{
                checkStrictly: true,
                value:'id',
                label:'name',
              }"
              clearable></el-cascader>
        </el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

	</section>
</template>

<script>
	import util from '../../common/js/util'
	//import NProgress from 'nprogress'
	import { getDepartmentListPage, removeDepartment, batchRemoveDepartment, editDepartment, addDepartment } from '../../api/api';

	export default {
		data() {
			return {
				filters: {
					keyword: ''
				},
        deptTree:[],
        employees:[],
				departments: [],
        dialogTitle:'',
				total: 0,
        currentPage:1,
        pageSize:10,
				//page: 1,
				listLoading: false,
				sels: [],//列表选中列

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					name: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					]
				},
				//编辑界面数据
        editForm: {
          id:null,
          sn:'',
          name:null,
          state:0,
          dirPath:'',
          manager:null,
          parent:null
				},

				/*addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					name: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					]
				},*/
				//新增界面数据
				// addForm: {
				// 	name: '',
				// 	sex: -1,
				// 	age: 0,
				// 	birth: '',
				// 	addr: ''
				// }

			}
		},
		methods: {
      departmentTree(){
        this.$http.post("/dept/tree").then(res=>{
            this.deptTree=res.data;
        })
      },
      selectEmployee(){
        this.$http.post("/emp/all").then(res=>{
            this.employees=res.data;
        })
      },
      selectDepartment(){
        this.currentPage=1
        this.getDepartments();
      },
			//状态显示转换
			formatState: function (row, column) {
				return row.state == 0 ? '正常' : row.state == -1 ? '禁用' : '未知';
			},
			handleCurrentChange(val) {
				this.currentPage = val;
				this.getDepartments();
			},
			//获取部门列表
			getDepartments() {
				let para = {
					pageSize:this.pageSize,
          currentPage:this.currentPage,
					keyword: this.filters.keyword
				};
        //忙等框
				this.listLoading = true;
        this.$http.post("/dept/query",para).then(res=>{
            //数据赋值
            this.departments=res.data.data.rows;
            this.total=res.data.data.total;
            //关闭忙等框
            this.listLoading = false;
        }).catch(res=>{
          this.$message({
            message: '服务器繁忙，请稍后重试',
            type: 'error'
          });
        })
				//NProgress.start();
				// getDepartmentListPage(para).then((res) => {
				// 	this.total = res.data.total;
				// 	this.departments = res.data.departments;
				// 	this.listLoading = false;
				// 	//NProgress.done();
				// });
			},
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = {
            id: row.id
          };
					this.$http.delete("/dept/"+para.id).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getDepartments();
					});
				}).catch(() => {

				});
			},
			//显示编辑界面
			handleEdit: function (index, row) {
        this.departmentTree();
        this.selectEmployee();
        this.dialogTitle="编辑";
        this.editFormVisible = true;
        let array = row.dirPath.split("/");
        let resultArr = [];
        for(let i= 1;i<= array.length-2;i++){
          resultArr.push(parseInt(array[i]))
        }
        row.parentDir = resultArr
        console.log(row.parent)

        //回显，这里是我们回显的当前行的数据
        //这句话就是在克隆对象，克隆岛我们的Object里面，赋值到editForm
				this.editForm = Object.assign({}, row);
			},
			//显示新增界面
			handleAdd: function () {
        this.dialogTitle="新增";
        //打开模态框
        this.editFormVisible = true;
				this.editForm = {
          id:null,
          sn:'',
          name:null,
          state:0,
          dirPath:'',
          manager:null,
          parent:null,
          parentDir:null
				};
			},
			//编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							//NProgress.start();

							let para = Object.assign({}, this.editForm);
              if(para.parentDir){
                para.parent={
                  id:para.parentDir[para.parentDir.length-1]
                }
              }
							this.$http.post("/dept/add",para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getDepartments();
                this.departmentTree();
							});
						});
					}
				});
			},
			//新增
			addSubmit: function () {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.addForm);
							para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							addDepartment(para).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['addForm'].resetFields();
								this.addFormVisible = false;
								this.getDepartments();
							});
						});
					}
				});
			},
			selsChange: function (sels) {

				this.sels = sels;
			},
			//批量删除
			batchRemove: function () {
				var ids = this.sels.map(item => item.id)//.toString();
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
          //两种方式，1、用数组接收  2、用list接收
					let para = {
            ids: ids
          };
					this.$http.patch("/dept/batch",para.ids).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getDepartments();
					});
				}).catch(() => {

				});
			}
		},
		mounted() {
			this.getDepartments();
      this.selectEmployee();
      this.departmentTree();
		}
	}

</script>

<style scoped>

</style>