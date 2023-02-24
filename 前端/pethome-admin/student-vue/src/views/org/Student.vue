 <template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.keyword" placeholder="学号 / 姓名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="selectStudent">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="students" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55" header-align="center" align="center">
			</el-table-column>
			<el-table-column type="index" width="60" header-align="center" align="center">
			</el-table-column>
      <el-table-column prop="sno" label="学号" width="130" sortable header-align="center" align="center">
      </el-table-column>
			<el-table-column prop="sname" label="姓名" width="140" sortable header-align="center" align="center">
			</el-table-column>
			<el-table-column prop="sex" label="性别" width="100" :formatter="formatSex" sortable header-align="center" align="center">
			</el-table-column>
			<el-table-column prop="grade" label="年级" width="150" sortable header-align="center" align="center">
			</el-table-column>
			<el-table-column prop="state" label="选课状态" width="150" sortable header-align="center" align="center">
        <template scope="scope">
          <span v-if="scope.row.state == 1" style="color: #11b95c">已选</span>
          <span v-else-if="scope.row.state == 0" style="color: #e64242">未选</span>
          <span v-else style="color: #5e7382">未知</span>
        </template>
			</el-table-column>
			<el-table-column prop="course.cname" label="选课科目" min-width="180" sortable header-align="center" align="center">
			</el-table-column>
			<el-table-column label="操作" width="200" header-align="center" align="center">
				<template scope="scope">
					<el-button type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)"></el-button>
					<el-button type="danger" icon="el-icon-delete" circle @click="handleDel(scope.$index, scope.row)"></el-button>
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

		<!--编辑界面-->
    <el-dialog :title="dialogTitle" :visible.sync="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="学号" prop="sno">
          <el-input v-model="editForm.sno" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="sname">
          <el-input v-model="editForm.sname" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="editForm.sex">
            <el-radio class="radio" :label="1">男</el-radio>
            <el-radio class="radio" :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-input v-model="editForm.grade" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="选课状态">
          <el-radio-group v-model="editForm.state">
            <el-radio class="radio" :label="1">已选</el-radio>
            <el-radio class="radio" :label="0">未选</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="选课科目">
          <el-select v-model="editForm.course"  value-key="id" placeholder="请选择">
            <el-option
                v-for="item in courses"
                :key="item.id"
                :label="item.cname"
                :value="item">
              <span style="float: left">{{ item.cname }}</span>
<!--              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.phone }}</span>-->
            </el-option>
          </el-select>
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
	export default {
		data() {
			return {
				filters: {
					keyword: ''
				},
        courses:[],
				students: [],
        dialogTitle:'',
				total: 0,
        currentPage:1,
				pageSize: 10,
				listLoading: false,
				sels: [],//列表选中列

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					sno: [
						{ required: true, message: '请输入学号', trigger: 'blur' }
					],
          sname: [
            { required: true, message: '请输入姓名', trigger: 'blur' }
          ],
          grade: [
            { required: true, message: '请输入年级', trigger: 'blur' }
          ]
				},
				//编辑界面数据
				editForm: {
					id: null,
          sno: '',
					sname: null,
					sex: 1,
					grade: null,
					state: '',
					course: null
				},

			}
		},
		methods: {
      selectCourse(){
        this.$http.post("/course/all").then(res=>{
          this.courses=res.data;
        })
      },
      selectStudent(){
        this.currentPage=1
        this.getStudents();
      },
			//性别显示转换
			formatSex: function (row, column) {
				return row.sex == 1 ? '男' : row.sex == 0 ? '女' : '未知';
			},
			handleCurrentChange(val) {
				this.currentPage = val;
				this.getStudents();
			},
			//获取用户列表.
			getStudents() {
				let para = {
					pageSize: this.pageSize,
          currentPage: this.currentPage,
          keyword: this.filters.keyword
				};
				this.listLoading = true;
        this.$http.post("/student/query",para).then(res=>{
          //数据赋值
          this.students=res.data.data.rows;
          this.total=res.data.data.total;
          //关闭忙等框
          this.listLoading = false;
        }).catch(res=>{
          this.$message({
            message: '服务器繁忙，请稍后重试',
            type: 'error'
          });
        })
			},
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { id: row.id };
          this.$http.delete("/student/"+para.id).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getStudents();
					});
				}).catch(() => {

				});
			},
			//显示编辑界面
      handleEdit: function (index, row) {
        this.selectCourse();
        this.dialogTitle="编辑";
        this.editFormVisible = true;
        this.editForm = Object.assign({}, row);
      },
			//显示新增界面
			handleAdd: function () {
        this.dialogTitle="新增";
				this.editFormVisible = true;
				this.editForm = {
          id: null,
					sno: '',
          sname: null,
					sex: 1,
					grade: null,
					state: 0,
					course: null
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
              this.$http.post("/student/add",para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getStudents();
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
					let para = { ids: ids };
          this.$http.patch("/student/batch",para.ids).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getStudents();
					});
				}).catch(() => {

				});
			}
		},
		mounted() {
			this.getStudents();
      this.selectCourse();
		}
	}

</script>

<style scoped>

</style>