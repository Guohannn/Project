using System;//修改完成
using System.Data;
using System.Windows.Forms;

namespace 学生宿舍管理系统
{
    public partial class 管理界面 : Form
    {
        private bool exit = true;
        public 管理界面()
        {
            InitializeComponent();
            学生信息管理界面.Show();
            分配宿舍面板.Hide();
            宿舍_楼.Hide();
            宿舍管理.Hide();
            调整宿舍面板.Hide();
            学生信息获取();
        }

        private void 宿舍楼获取()
        {
            DataSet ds = new DataSet();
            ds = database.获取数据
                ("select * from Building ");
            if (ds != null)
            {
                宿舍楼表.DataSource = ds.Tables[0];
                宿舍楼表.Columns["bid"].HeaderText = "楼号";
                宿舍楼表.Columns["num"].HeaderText = "宿舍数";
                楼号下拉表.DataSource = ds.Tables[0];
                楼号下拉表.DisplayMember = "bid";
            }
            else
                宿舍楼表.DataSource = null;
        }

        private void 调整表获取()
        {
            DataSet ds = new DataSet();
            ds = database.获取数据
                ("select * from Adjust ");
            if (ds != null)
            {
                调整表.DataSource = ds.Tables[0];  
                调整表.DataSource = ds.Tables[0];
                调整表.Columns["sid"].HeaderText = "学号";
                调整表.Columns["adate"].HeaderText = "时间";
                调整表.Columns["olddid"].HeaderText = "原宿舍";
                调整表.Columns["newdid"].HeaderText = "调后宿舍";
                调整表.Columns["reason"].HeaderText = "原因";
            }
            else
                调整表.DataSource = null;
        }

        private void 宿舍获取()
        {
            DataSet ds = new DataSet();
            ds = database.获取数据
                ("select * from Dormitory ");
            if (ds != null)
            {
                宿舍信息表.DataSource = ds.Tables[0];
                宿舍信息表.Columns["did"].HeaderText = "宿舍序号";
                宿舍信息表.Columns["bid"].HeaderText = "楼号";
                宿舍信息表.Columns["dnum"].HeaderText = "宿舍号";
                宿舍信息表.Columns["floor"].HeaderText = "楼层";
                宿舍信息表.Columns["bed"].HeaderText = "床位数";
            }
            else
                宿舍信息表.DataSource = null;
        }

        private void 空余宿舍获取()
        {
            DataSet ds = new DataSet();
            DataTable dt = new DataTable();
            DataSet number = new DataSet();
            ds = database.获取数据
                ("select * from Dormitory where bed> (select COUNT (StuDormitory.did) from StuDormitory where StuDormitory.did=Dormitory.did) ");
            if (ds != null)
            {
                int count = ds.Tables[0].Rows.Count;
                dt.Columns.Add("did", typeof(string));//添加一列
                dt.Columns.Add("bid", typeof(string));//添加一列
                dt.Columns.Add("dnum", typeof(string));//添加一列  
                dt.Columns.Add("floor", typeof(string));//添加一列
                dt.Columns.Add("bed", typeof(string));//添加一列 
                dt.Columns.Add("allow", typeof(string));//添加一列
                for (int i = 0; i < count; i++)
                {
                    DataRow dr1 = dt.NewRow();
                    dr1["did"] = ds.Tables[0].Rows[i]["did"].ToString();
                    dr1["bid"] = ds.Tables[0].Rows[i]["bid"].ToString();
                    dr1["dnum"] = ds.Tables[0].Rows[i]["dnum"].ToString();
                    try
                    {
                        number = database.获取数据
                ("select COUNT(did) from StuDormitory where StuDormitory.did='" + ds.Tables[0].Rows[i]["did"].ToString() + "'");
                        dr1["allow"] = (Convert.ToInt32(ds.Tables[0].Rows[i]["bed"].ToString()) - Convert.ToInt32(number.Tables[0].Rows[0][0])).ToString();
                    }
                    catch
                    {
                        dr1["allow"] = "计算失败";
                    }
                    dr1["floor"] = ds.Tables[0].Rows[i]["floor"].ToString();
                    dr1["bed"] = ds.Tables[0].Rows[i]["bed"].ToString();
                    dt.Rows.InsertAt(dr1, 0);
                }
                dataGridView1.DataSource = dt;
                dataGridView1.Columns["did"].HeaderText = "宿舍序号";
                dataGridView1.Columns["bid"].HeaderText = "楼号";
                dataGridView1.Columns["dnum"].HeaderText = "宿舍号";
                dataGridView1.Columns["floor"].HeaderText = "楼层";
                dataGridView1.Columns["bed"].HeaderText = "床位数";
                dataGridView1.Columns["allow"].HeaderText = "剩余床位";
            }
            else
                dataGridView1.DataSource = null;
        }

        private void 新宿舍表获取()
        {
            DataSet ds = new DataSet();
            DataTable dt = new DataTable();
            DataSet number = new DataSet();
            ds = database.获取数据
                ("select * from Dormitory where bed> (select COUNT (StuDormitory.did) from StuDormitory where StuDormitory.did=Dormitory.did) ");
            if (ds != null)
            {
                int count = ds.Tables[0].Rows.Count;
                dt.Columns.Add("did", typeof(string));//添加一列
                dt.Columns.Add("bid", typeof(string));//添加一列
                dt.Columns.Add("dnum", typeof(string));//添加一列  
                dt.Columns.Add("floor", typeof(string));//添加一列
                dt.Columns.Add("bed", typeof(string));//添加一列 
                dt.Columns.Add("allow", typeof(string));//添加一列
                for (int i = 0; i < count; i++)
                {
                    DataRow dr1 = dt.NewRow();
                    dr1["did"] = ds.Tables[0].Rows[i]["did"].ToString();
                    dr1["bid"] = ds.Tables[0].Rows[i]["bid"].ToString();
                    dr1["dnum"] = ds.Tables[0].Rows[i]["dnum"].ToString();
                    try
                    {
                        number = database.获取数据
                ("select COUNT(did) from StuDormitory where StuDormitory.did='" + ds.Tables[0].Rows[i]["did"].ToString() + "'");
                        dr1["allow"] = (Convert.ToInt32(ds.Tables[0].Rows[i]["bed"].ToString()) - Convert.ToInt32(number.Tables[0].Rows[0][0])).ToString();
                    }
                    catch
                    {
                        dr1["allow"] = "计算失败";
                    }
                    dr1["floor"] = ds.Tables[0].Rows[i]["floor"].ToString();
                    dr1["bed"] = ds.Tables[0].Rows[i]["bed"].ToString();
                    dt.Rows.InsertAt(dr1, 0);
                }
                新宿舍表.DataSource = dt;
                新宿舍表.Columns["did"].HeaderText = "宿舍序号";
                新宿舍表.Columns["bid"].HeaderText = "楼号";
                新宿舍表.Columns["dnum"].HeaderText = "宿舍号";
                新宿舍表.Columns["floor"].HeaderText = "楼层";
                新宿舍表.Columns["bed"].HeaderText = "床位数";
                新宿舍表.Columns["allow"].HeaderText = "剩余床位";
            }
            else
                新宿舍表.DataSource = null;
        }

        private void 学生信息获取()
        {
            DataSet ds = new DataSet();
            DataTable dt = new DataTable();
            ds = database.获取数据("select * from Student ");
            if (ds != null)
            {
                int count = ds.Tables[0].Rows.Count;
                dt.Columns.Add("sid", typeof(string));//添加一列
                dt.Columns.Add("name", typeof(string));//添加一列
                dt.Columns.Add("sex", typeof(string));//添加一列
                dt.Columns.Add("birth", typeof(string));//添加一列
                dt.Columns.Add("year", typeof(string));//添加一列
                dt.Columns.Add("college", typeof(string));//添加一列
                dt.Columns.Add("class", typeof(string));//添加一列
                dt.Columns.Add("phone", typeof(string));//添加一列
                for (int i = 0; i < count; i++)
                {
                    DataRow dr1 = dt.NewRow();
                    dr1["sid"] = ds.Tables[0].Rows[i]["sid"].ToString();
                    dr1["name"] = ds.Tables[0].Rows[i]["name"].ToString();
                    dr1["sex"] = ds.Tables[0].Rows[i]["sex"].ToString();
                    dr1["birth"] = ds.Tables[0].Rows[i]["birth"].ToString();
                    dr1["year"] = (DateTime.Now.Year - Convert.ToDateTime(ds.Tables[0].Rows[i]["birth"].ToString()).Year).ToString() + "岁";
                    dr1["college"] = ds.Tables[0].Rows[i]["college"].ToString();
                    dr1["class"] = ds.Tables[0].Rows[i]["class"].ToString();
                    dr1["phone"] = ds.Tables[0].Rows[i]["phone"].ToString();
                    dt.Rows.InsertAt(dr1, 0);
                }
                学生信息表.DataSource = dt;
                学生信息表.Columns["birth"].HeaderText = "出生日期";
                学生信息表.Columns["sid"].HeaderText = "学号";
                学生信息表.Columns["name"].HeaderText = "姓名";
                学生信息表.Columns["sex"].HeaderText = "性别";
                学生信息表.Columns["college"].HeaderText = "学院";
                学生信息表.Columns["class"].HeaderText = "班级";
                学生信息表.Columns["phone"].HeaderText = "电话";
                学生信息表.Columns["year"].HeaderText = "年龄";
                学生信息表.Columns["year"].DisplayIndex = 3;
            }
            else
                学生信息表.DataSource = null;
        }

        private void 宿舍更新(string str)
        {
            DataSet ds = new DataSet();
            ds = database.获取数据(str);
            if (ds != null)
            {
                宿舍信息表.DataSource = ds.Tables[0];
                宿舍信息表.Columns["did"].HeaderText = "宿舍序号";
                宿舍信息表.Columns["bid"].HeaderText = "楼号";
                宿舍信息表.Columns["dnum"].HeaderText = "宿舍号";
                宿舍信息表.Columns["floor"].HeaderText = "楼层";
                宿舍信息表.Columns["bed"].HeaderText = "床位数";
            }
            else
                宿舍信息表.DataSource = null;
        }

        private void 宿舍楼更新(string str)
        {
            DataSet ds = new DataSet();
            ds = database.获取数据(str);
            if (ds != null)
            {
                宿舍楼表.DataSource = ds.Tables[0];
                宿舍楼表.Columns["bid"].HeaderText = "楼号";
                宿舍楼表.Columns["num"].HeaderText = "宿舍数";
            }
            else
                宿舍楼表.DataSource = null;
        }

        private void 学生更新(string str)
        {
            DataSet ds = new DataSet();
            DataTable dt = new DataTable();
            ds = database.获取数据(str);
            if (ds != null)
            {
                int count = ds.Tables[0].Rows.Count;
                dt.Columns.Add("sid", typeof(string));//添加一列
                dt.Columns.Add("name", typeof(string));//添加一列
                dt.Columns.Add("sex", typeof(string));//添加一列
                dt.Columns.Add("birth", typeof(string));//添加一列
                dt.Columns.Add("year", typeof(string));//添加一列
                dt.Columns.Add("college", typeof(string));//添加一列
                dt.Columns.Add("class", typeof(string));//添加一列
                dt.Columns.Add("phone", typeof(string));//添加一列
                for (int i = 0; i < count; i++)
                {
                    DataRow dr1 = dt.NewRow();
                    dr1["sid"] = ds.Tables[0].Rows[i]["sid"].ToString();
                    dr1["name"] = ds.Tables[0].Rows[i]["name"].ToString();
                    dr1["sex"] = ds.Tables[0].Rows[i]["sex"].ToString();
                    dr1["birth"] = ds.Tables[0].Rows[i]["birth"].ToString();
                    dr1["year"] = (DateTime.Now.Year - Convert.ToDateTime(ds.Tables[0].Rows[i]["birth"].ToString()).Year).ToString() + "岁";
                    dr1["college"] = ds.Tables[0].Rows[i]["college"].ToString();
                    dr1["class"] = ds.Tables[0].Rows[i]["class"].ToString();
                    dr1["phone"] = ds.Tables[0].Rows[i]["phone"].ToString();
                    dt.Rows.InsertAt(dr1, 0);
                }
                学生信息表.DataSource = dt;
                学生信息表.Columns["birth"].HeaderText = "出生日期";
                学生信息表.Columns["sid"].HeaderText = "学号";
                学生信息表.Columns["name"].HeaderText = "姓名";
                学生信息表.Columns["sex"].HeaderText = "性别";
                学生信息表.Columns["college"].HeaderText = "学院";
                学生信息表.Columns["class"].HeaderText = "班级";
                学生信息表.Columns["phone"].HeaderText = "电话";
                学生信息表.Columns["year"].HeaderText = "年龄";
                学生信息表.Columns["year"].DisplayIndex = 3;
            }
            else
                学生信息表.DataSource = null;
        }

        private void 分配宿舍_学生信息获取()
        {
            DataSet ds = new DataSet();
            DataTable dt = new DataTable();
            ds = database.获取数据(@"select * from Student where not exists(
                select * from StuDormitory where sid=Student.sid )");
            if (ds != null)
            {
                int count = ds.Tables[0].Rows.Count;
                dt.Columns.Add("sid", typeof(string));//添加一列
                dt.Columns.Add("name", typeof(string));//添加一列
                dt.Columns.Add("sex", typeof(string));//添加一列
                dt.Columns.Add("birth", typeof(string));//添加一列
                dt.Columns.Add("year", typeof(string));//添加一列
                dt.Columns.Add("college", typeof(string));//添加一列
                dt.Columns.Add("class", typeof(string));//添加一列
                dt.Columns.Add("phone", typeof(string));//添加一列
                for (int i = 0; i < count; i++)
                {
                    DataRow dr1 = dt.NewRow();
                    dr1["sid"] = ds.Tables[0].Rows[i]["sid"].ToString();
                    dr1["name"] = ds.Tables[0].Rows[i]["name"].ToString();
                    dr1["sex"] = ds.Tables[0].Rows[i]["sex"].ToString();
                    dr1["birth"] = ds.Tables[0].Rows[i]["birth"].ToString();
                    dr1["year"] = (DateTime.Now.Year - Convert.ToDateTime(ds.Tables[0].Rows[i]["birth"].ToString()).Year).ToString() + "岁";
                    dr1["college"] = ds.Tables[0].Rows[i]["college"].ToString();
                    dr1["class"] = ds.Tables[0].Rows[i]["class"].ToString();
                    dr1["phone"] = ds.Tables[0].Rows[i]["phone"].ToString();
                    dt.Rows.InsertAt(dr1, 0);
                }
                分配宿舍_学生信息.DataSource = dt;
                分配宿舍_学生信息.Columns["birth"].HeaderText = "出生日期";
                分配宿舍_学生信息.Columns["sid"].HeaderText = "学号";
                分配宿舍_学生信息.Columns["name"].HeaderText = "姓名";
                分配宿舍_学生信息.Columns["sex"].HeaderText = "性别";
                分配宿舍_学生信息.Columns["college"].HeaderText = "学院";
                分配宿舍_学生信息.Columns["class"].HeaderText = "班级";
                分配宿舍_学生信息.Columns["phone"].HeaderText = "电话";
                分配宿舍_学生信息.Columns["year"].HeaderText = "年龄";
                分配宿舍_学生信息.Columns["year"].DisplayIndex = 3;
            }
            else
                分配宿舍_学生信息.DataSource = null;
        }

        private void 分配宿舍_学生信息更新(string str)
        {
            DataSet ds = new DataSet();
            DataTable dt = new DataTable();
            ds = database.获取数据(str);
            if (ds != null)
            {
                int count = ds.Tables[0].Rows.Count;
                dt.Columns.Add("sid", typeof(string));//添加一列
                dt.Columns.Add("name", typeof(string));//添加一列
                dt.Columns.Add("sex", typeof(string));//添加一列
                dt.Columns.Add("birth", typeof(string));//添加一列
                dt.Columns.Add("year", typeof(string));//添加一列
                dt.Columns.Add("college", typeof(string));//添加一列
                dt.Columns.Add("class", typeof(string));//添加一列
                dt.Columns.Add("phone", typeof(string));//添加一列
                for (int i = 0; i < count; i++)
                {
                    DataRow dr1 = dt.NewRow();
                    dr1["sid"] = ds.Tables[0].Rows[i]["sid"].ToString();
                    dr1["name"] = ds.Tables[0].Rows[i]["name"].ToString();
                    dr1["sex"] = ds.Tables[0].Rows[i]["sex"].ToString();
                    dr1["birth"] = ds.Tables[0].Rows[i]["birth"].ToString();
                    dr1["year"] = (DateTime.Now.Year - Convert.ToDateTime(ds.Tables[0].Rows[i]["birth"].ToString()).Year).ToString() + "岁";
                    dr1["college"] = ds.Tables[0].Rows[i]["college"].ToString();
                    dr1["class"] = ds.Tables[0].Rows[i]["class"].ToString();
                    dr1["phone"] = ds.Tables[0].Rows[i]["phone"].ToString();
                    dt.Rows.InsertAt(dr1, 0);
                }
                分配宿舍_学生信息.DataSource = dt;
                分配宿舍_学生信息.Columns["birth"].HeaderText = "出生日期";
                分配宿舍_学生信息.Columns["sid"].HeaderText = "学号";
                分配宿舍_学生信息.Columns["name"].HeaderText = "姓名";
                分配宿舍_学生信息.Columns["sex"].HeaderText = "性别";
                分配宿舍_学生信息.Columns["college"].HeaderText = "学院";
                分配宿舍_学生信息.Columns["class"].HeaderText = "班级";
                分配宿舍_学生信息.Columns["phone"].HeaderText = "电话";
                分配宿舍_学生信息.Columns["year"].HeaderText = "年龄";
                分配宿舍_学生信息.Columns["year"].DisplayIndex = 3;
            }
            else
                分配宿舍_学生信息.DataSource = null;
        }

        private void 删除_Click(object sender, EventArgs e)
        {
            if (楼号文本框.Text.Trim().Equals(""))
                MessageBox.Show("请输入楼号！", "提示");
            else if (!IsNotExist_bid(楼号文本框.Text.ToString().Trim()))
            {
                string sqlStr1;
                if (MessageBox.Show("确定要删除该记录吗？无法撤销！", "删除", MessageBoxButtons.YesNo, MessageBoxIcon.Question, MessageBoxDefaultButton.Button2) == DialogResult.Yes)
                {
                    try
                    {
                        sqlStr1 = "delete from Building where bid='" + 楼号文本框.Text.Trim() + "'";
                        database.更新(sqlStr1);
                        宿舍楼获取();
                        MessageBox.Show("删除成功！", "提示");
                    }
                    catch
                    {
                        MessageBox.Show("操作失败！请检查规范", "提示");
                    }
                }
                else return;
            }
            else
            {
                MessageBox.Show("该记录不存在！", "提示");
                return;
            }
        }

        private void 管理界面_Load(object sender, EventArgs e)
        {
            宿舍楼获取();
            宿舍_楼.Show();
        }

        private void 宿舍楼表_RowHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            int n = 宿舍楼表.CurrentCell.RowIndex;
            if (n < 宿舍楼表.RowCount)
            {
                楼号文本框.Text = 宿舍楼表[0, n].Value.ToString().Trim();
                宿舍文本框.Text = 宿舍楼表[1, n].Value.ToString().Trim();
            }
        }

        private void 添加_Click(object sender, EventArgs e)
        {
            if (楼号文本框.Text.Trim().Equals("") || 宿舍文本框.Text.Trim().Equals(""))
            {
                if (楼号文本框.Text.Trim().Equals("")) MessageBox.Show("请输入楼号！", "提示");
                else if (宿舍文本框.Text.Trim().Equals("")) MessageBox.Show("请输入宿舍数！", "提示");
            }
            else if (!IsNotExist_bid(楼号文本框.Text.ToString().Trim()))
            {
                MessageBox.Show("该记录已存在！", "提示");
                return;
            }
            else
            {
                string sqlStr1;
                try
                {
                    sqlStr1 = "insert into Building values('" + 楼号文本框.Text.Trim() + "','"
                         + 宿舍文本框.Text.Trim() + "')";
                    database.更新(sqlStr1);
                    宿舍楼获取();
                    MessageBox.Show("添加成功！", "提示");
                }
                catch
                {
                    MessageBox.Show("操作失败！请检查规范", "提示");
                }
            }
        }

        private void 管理界面_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (exit)
            {
                this.FormClosing -= new FormClosingEventHandler(this.管理界面_FormClosing);
                Application.Exit();
            }
        }

        private void 退出登录ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            exit = false;
            this.Close();
            登录.id = 0;
            new 登录().Show();
        }

        private bool IsNotExist_bid(string no)
        {
            int n = 宿舍楼表.Rows.Count;
            for (int i = 0; i < n; i++)
            {
                if (no.Equals(宿舍楼表.Rows[i].Cells[0].Value.ToString().Trim()))
                    return false;
            }
            return true;
        }

        private bool IsNotExist_did(string no)
        {
            int n = 宿舍信息表.Rows.Count;
            for (int i = 0; i < n; i++)
            {
                if (no.Equals(宿舍信息表.Rows[i].Cells[0].Value.ToString().Trim()))
                    return false;
            }
            return true;
        }

        private bool IsNotchongfu_d_b(string did, string dnum, string bid)
        {
            DataSet ds = new DataSet();
            ds = database.获取数据
                ("select * from Dormitory where did !='" + did + "'and dnum='" + dnum + "'" + "and bid='" + bid + "'");
            if (ds != null)
                return false;
            else
                return true;
        }

        private bool IsNotchongfu_s_d(string did, string sid)
        {
            DataSet ds = new DataSet();
            ds = database.获取数据
                ("select * from StuDormitory where did ='" + did + "'and sid='" + sid + "'");
            if (ds != null)
                return false;
            else
                return true;
        }

        private bool IsExit_sid(string sid)
        {
            DataSet ds = new DataSet();
            DataTable dt = new DataTable();
            ds = database.获取数据("select * from Student where sid='" + sid + "'");
            if (ds != null)
            {
                int count = ds.Tables[0].Rows.Count;
                dt.Columns.Add("sid", typeof(string));//添加一列
                dt.Columns.Add("name", typeof(string));//添加一列
                dt.Columns.Add("sex", typeof(string));//添加一列
                dt.Columns.Add("year", typeof(string));//添加一列
                dt.Columns.Add("college", typeof(string));//添加一列
                dt.Columns.Add("class", typeof(string));//添加一列
                dt.Columns.Add("phone", typeof(string));//添加一列
                for (int i = 0; i < count; i++)
                {
                    DataRow dr1 = dt.NewRow();
                    dr1["sid"] = ds.Tables[0].Rows[i]["sid"].ToString();
                    dr1["name"] = ds.Tables[0].Rows[i]["name"].ToString();
                    dr1["sex"] = ds.Tables[0].Rows[i]["sex"].ToString();
                    dr1["year"] = (DateTime.Now.Year - Convert.ToDateTime(ds.Tables[0].Rows[i]["birth"].ToString()).Year).ToString() + "岁";
                    dr1["college"] = ds.Tables[0].Rows[i]["college"].ToString();
                    dr1["class"] = ds.Tables[0].Rows[i]["class"].ToString();
                    dr1["phone"] = ds.Tables[0].Rows[i]["phone"].ToString();
                    dt.Rows.InsertAt(dr1, 0);
                }
                调整宿舍_学生信息表.DataSource = dt;
                调整宿舍_学生信息表.Columns["sid"].HeaderText = "学号";
                调整宿舍_学生信息表.Columns["name"].HeaderText = "姓名";
                调整宿舍_学生信息表.Columns["sex"].HeaderText = "性别";
                调整宿舍_学生信息表.Columns["college"].HeaderText = "学院";
                调整宿舍_学生信息表.Columns["class"].HeaderText = "班级";
                调整宿舍_学生信息表.Columns["phone"].HeaderText = "电话";
                调整宿舍_学生信息表.Columns["year"].HeaderText = "年龄";
                调整宿舍_学生信息表.Columns["year"].DisplayIndex = 3;
                return true;
            }
            else
            {
                调整宿舍_学生信息表.DataSource = null;
                return false;
            }
        }
        private bool IsNotExist_sid(string no)
        {
            int n = 学生信息表.Rows.Count;
            for (int i = 0; i < n; i++)
            {
                if (no.Equals(学生信息表.Rows[i].Cells[0].Value.ToString().Trim()))
                    return false;
            }
            return true;
        }

        private bool IsNotExist_OtherSex(string did, string sid)
        {
            DataSet ds = new DataSet();
            ds = database.获取数据
                (@"select * from StuDormitory,Student 
                  where StuDormitory.sid=Student.sid and did='" + did +
                   "'and sex=(select sex from Student where sid='" + sid + "')");
            if (ds != null)
                return true;
            else
            {
                ds = database.获取数据
                (@"select * from StuDormitory
                  where  did='" + did + "'");
                if (ds != null)
                    return false;
                else
                    return true;
            }
        }

        private void 取消_Click(object sender, EventArgs e)
        {
            楼号文本框.Text = "";
            宿舍文本框.Text = "";
            查询值文本框.Text = "";
        }

        private void 管理菜单栏_ItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {

        }

        private void 管理宿舍ToolStripMenuItem_Click(object sender, EventArgs e)
        {

        }

        private void 修改_Click(object sender, EventArgs e)
        {
            if (楼号文本框.Text.Trim().Equals("") || 宿舍文本框.Text.Trim().Equals(""))
            {
                if (楼号文本框.Text.Trim().Equals(""))
                    MessageBox.Show("请输入楼号！", "提示");
                else if (宿舍文本框.Text.Trim().Equals(""))
                    MessageBox.Show("请输入宿舍数！", "提示");
            }
            else if (IsNotExist_bid(楼号文本框.Text.ToString().Trim()))
            {
                MessageBox.Show("该记录不存在！", "提示");
                return;
            }
            else
            {
                string sqlStr1;
                try
                {
                    sqlStr1 = "update Building set bid='" + 楼号文本框.Text.Trim() +
                          "',num='" + 宿舍文本框.Text.Trim()+
                           "'where bid='" + 楼号文本框.Text.Trim() + "'";
                    database.更新(sqlStr1);
                    宿舍楼获取();
                    MessageBox.Show("修改成功！", "提示");
                }
                catch
                {
                    MessageBox.Show("操作失败！请检查规范", "提示");
                }
            }
        }

        private void 查询_Click(object sender, EventArgs e)
        {
            if (查询条件.SelectedIndex == -1 || 查询值文本框.Text.Equals(""))
                MessageBox.Show("查询信息不完整", "提示");
            else
            {
                string sqlStr1 = "";
                if (查询条件.SelectedIndex == 0)
                    sqlStr1 = " select * from Building where bid='" + 查询值文本框.Text.Trim() + "'";
                else if (查询条件.SelectedIndex == 1)
                    sqlStr1 = " select * from Building where num='" + 查询值文本框.Text.Trim() + "'";
                if (sqlStr1.Equals(""))
                {
                    MessageBox.Show("查询失败", "提示");
                    return;
                }
                try
                {
                    宿舍楼更新(sqlStr1);
                    MessageBox.Show("查询成功！", "提示");
                }
                catch
                {
                    MessageBox.Show("没有结果!", "提示");
                }
            }
        }

        private void 宿舍楼管理ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            {
                宿舍_楼.Show();
                宿舍管理.Hide();
                调整宿舍面板.Hide();
                学生信息管理界面.Hide();
                分配宿舍面板.Hide();
                宿舍楼获取();
            }
        }

        private void 宿舍管理ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            宿舍管理.Show();
            宿舍_楼.Hide();
            学生信息管理界面.Hide();
            分配宿舍面板.Hide();
            调整宿舍面板.Hide();
            宿舍楼获取();
            宿舍获取();
            楼号下拉表.Text = "";
            宿舍楼号文本框.Text = 楼号下拉表.Text;
        }
        
        private void label6_Click(object sender, EventArgs e)
        {

        }

        private void 添加宿舍_Click(object sender, EventArgs e)
        {
            if (宿舍序号.Text.Trim().Equals("") || 宿舍楼号文本框.Text.Trim().Equals("") || 宿舍号.Text.Trim().Equals("") || 楼层.Text.Trim().Equals("") || 床位数.Text.Trim().Equals(""))
            {
                if (宿舍序号.Text.Trim().Equals(""))
                    MessageBox.Show("请输入宿舍序号！", "提示");
                else if (宿舍楼号文本框.Text.Trim().Equals(""))
                    MessageBox.Show("请选择楼号！", "提示");
                else if (宿舍号.Text.Trim().Equals(""))
                    MessageBox.Show("请输入宿舍号！", "提示");
                else if (楼层.Text.Trim().Equals(""))
                    MessageBox.Show("请输入楼层！", "提示");
                else if (床位数.Text.Trim().Equals(""))
                    MessageBox.Show("请输入床位数！", "提示");
            }
            else if (!IsNotExist_did(宿舍序号.Text.ToString().Trim()) || !IsNotchongfu_d_b(宿舍序号.Text.Trim(), 宿舍号.Text.Trim(), 宿舍楼号文本框.Text.Trim()))
            {
                MessageBox.Show("该记录已存在！", "提示");
                return;
            }
            else
            {
                string sqlStr1;
                try
                {
                    sqlStr1 = "insert into Dormitory values('" + 宿舍序号.Text.Trim() + "','"
                         + 宿舍楼号文本框.Text.Trim() + "','" + 宿舍号.Text.Trim() + "','" + 楼层.Text.Trim() + "','" + 床位数.Text.Trim()  + "')";
                    database.更新(sqlStr1);
                    宿舍获取();
                    MessageBox.Show("添加成功！", "提示");
                }
                catch
                {
                    MessageBox.Show("操作失败！请检查规范", "提示");
                }
            }
        }

        private void 宿舍信息表_RowHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            int n = 宿舍信息表.CurrentCell.RowIndex;
            if (n < 宿舍信息表.RowCount)
            {
                宿舍序号.Text = 宿舍信息表[0, n].Value.ToString().Trim();
                楼号下拉表.Text = 宿舍信息表[1, n].Value.ToString().Trim();
                宿舍楼号文本框.Text = 宿舍信息表[1, n].Value.ToString().Trim();
                宿舍号.Text = 宿舍信息表[2, n].Value.ToString().Trim();
                楼层.Text = 宿舍信息表[3, n].Value.ToString().Trim();
                床位数.Text = 宿舍信息表[4, n].Value.ToString().Trim();
            }
        }

        private void 修改宿舍_Click_1(object sender, EventArgs e)
        {
            if (宿舍序号.Text.Trim().Equals("") || 宿舍楼号文本框.Text.Trim().Equals("") || 宿舍号.Text.Trim().Equals("") || 楼层.Text.Trim().Equals("") || 床位数.Text.Trim().Equals("") )
            {
                if (宿舍序号.Text.Trim().Equals(""))
                    MessageBox.Show("请输入宿舍序号！", "提示");
                else if (宿舍楼号文本框.Text.Trim().Equals(""))
                    MessageBox.Show("请选择楼号！", "提示");
                else if (宿舍号.Text.Trim().Equals(""))
                    MessageBox.Show("请输入宿舍号！", "提示");
                else if (楼层.Text.Trim().Equals(""))
                    MessageBox.Show("请输入楼层！", "提示");
                else if (床位数.Text.Trim().Equals(""))
                    MessageBox.Show("请输入床位数！", "提示");
            }
            else if (IsNotExist_did(宿舍序号.Text.ToString().Trim()))
            {
                MessageBox.Show("该记录不存在！", "提示");
                return;
            }
            else if (!IsNotchongfu_d_b(宿舍序号.Text.Trim(), 宿舍号.Text.Trim(), 宿舍楼号文本框.Text.Trim()))
            {
                MessageBox.Show("该宿舍记录已存在！", "提示");
                return;
            }
            else
            {
                string sqlStr1;
                try
                {
                    sqlStr1 = "update  Dormitory set did='" + 宿舍序号.Text.Trim() + "',bid='"
                        + 宿舍楼号文本框.Text.Trim() + "',dnum='" + 宿舍号.Text.Trim() + "',floor='"
                        + 楼层.Text.Trim() + "',bed='" + 床位数.Text.Trim() 
                        + "'where did='" + 宿舍序号.Text.Trim() + "'";
                    database.更新(sqlStr1);
                    宿舍获取();
                    MessageBox.Show("修改成功！", "提示");
                }
                catch
                {
                    MessageBox.Show("操作失败！请检查规范", "提示");
                }
            }
        }

        private void 删除宿舍_Click(object sender, EventArgs e)
        {
            if (宿舍序号.Text.Trim().Equals(""))
                MessageBox.Show("请输入宿舍序号！", "提示");
            else if (!IsNotExist_did(宿舍序号.Text.ToString().Trim()))
            {
                string sqlStr1;
                if (MessageBox.Show("确定要删除该记录吗？无法撤销！", "删除", MessageBoxButtons.YesNo, MessageBoxIcon.Question, MessageBoxDefaultButton.Button2) == DialogResult.Yes)
                {
                    try
                    {
                        sqlStr1 = "delete from Dormitory where did='" + 宿舍序号.Text.Trim() + "'";
                        database.更新(sqlStr1);
                        宿舍获取();
                        MessageBox.Show("删除成功！", "提示");
                    }
                    catch
                    {
                        MessageBox.Show("操作失败！请检查规范", "提示");
                    }
                }
                else return;
            }
            else
            {
                MessageBox.Show("该记录不存在！", "提示");
                return;
            }
        }

        private void 宿舍信息_取消_Click(object sender, EventArgs e)
        {
            宿舍序号.Text = "";
            楼号下拉表.Text = "";
            宿舍楼号文本框.Text = "";
            宿舍号.Text = "";
            楼层.Text = "";
            床位数.Text = "";
            宿舍查询值.Text = "";
        }

        private void 宿舍_查询_Click(object sender, EventArgs e)
        {
            if (宿舍查询条件.Text.Trim().Equals("") || 宿舍查询值.Text.Equals(""))
                MessageBox.Show("查询信息不完整", "提示");
            else
            {
                string sqlStr1 = "";
                switch (宿舍查询条件.SelectedIndex)
                {
                    case 0: { sqlStr1 = " select * from Dormitory where did='" + 宿舍查询值.Text.Trim() + "'"; } break;
                    case 1: { sqlStr1 = " select * from Dormitory where bid='" + 宿舍查询值.Text.Trim() + "'"; } break;
                    case 2: { sqlStr1 = " select * from Dormitory where dnum='" + 宿舍查询值.Text.Trim() + "'"; } break;
                    case 3: { sqlStr1 = " select * from Dormitory where floor='" + 宿舍查询值.Text.Trim() + "'"; } break;
                    case 4: { sqlStr1 = " select * from Dormitory where bed='" + 宿舍查询值.Text.Trim() + "'"; } break;
                }
                if (sqlStr1.Equals(""))
                {
                    MessageBox.Show("查询失败", "提示");
                    return;
                }
                try
                {
                    宿舍更新(sqlStr1);
                    MessageBox.Show("查询成功！", "提示");
                }
                catch
                {
                    MessageBox.Show("没有结果!", "提示");
                }
            }
        }

        private void 楼号下拉表_TextChanged(object sender, EventArgs e)
        {
            宿舍楼号文本框.Text = 楼号下拉表.Text;
        }

        private void 学生信息管理ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            学生信息管理界面.Show();
            分配宿舍面板.Hide();
            宿舍_楼.Hide();
            宿舍管理.Hide();
            调整宿舍面板.Hide();
            学生信息获取();
        }

        private void 学生信息表_RowHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            int n = 学生信息表.CurrentCell.RowIndex;
            if (n < 学生信息表.RowCount)
            {
                学生信息_出生日期.Value = Convert.ToDateTime(学生信息表[3, n].Value.ToString().Trim());
                学生信息_学号.Text = 学生信息表[0, n].Value.ToString().Trim();
                学生信息_姓名.Text = 学生信息表[1, n].Value.ToString().Trim();
                学生信息_性别.Text = 学生信息表[2, n].Value.ToString().Trim();
                学生信息_年龄.Text = 学生信息表[4, n].Value.ToString().Trim();
                学生信息_所在学院.Text = 学生信息表[5, n].Value.ToString().Trim();
                学生信息_所在班.Text = 学生信息表[6, n].Value.ToString().Trim();
                学生信息_电话.Text = 学生信息表[7, n].Value.ToString().Trim();
            }
        }

        private void 学生信息_添加_Click(object sender, EventArgs e)
        {
            if (学生信息_出生日期.Value.ToString().Equals("") || 学生信息_学号.Text.Trim().Equals("") ||
                学生信息_姓名.Text.Trim().Equals("") || 学生信息_性别.Text.Trim().Equals("") || 学生信息_年龄.Text.Trim().Equals("") ||
                学生信息_所在学院.Text.Trim().Equals("") || 学生信息_所在班.Text.Trim().Equals(""))
            {
                MessageBox.Show("信息不完整！", "提示");
                return;
            }
            else if (IsNotExist_sid(学生信息_学号.Text.Trim()))
            {
                string sqlStr1 ;
                try
                {
                    sqlStr1 = "insert into Student values ('" + 学生信息_学号.Text.Trim() + "','"
                         + 学生信息_姓名.Text.Trim() + "','" + 学生信息_性别.Text.Trim() + "','" + 学生信息_出生日期.Value.ToShortDateString().ToString().Trim()
                         + "','" + 学生信息_所在学院.Text.Trim() + "','" + 学生信息_所在班.Text.Trim() + "','"
                         + 学生信息_电话.Text.ToString().Trim() + "','123456')";
                    database.更新(sqlStr1);
                    学生信息获取();
                    MessageBox.Show("执行完成！", "提示");
                }
                catch
                {
                    MessageBox.Show("操作失败！请检查规范", "提示");
                    return;
                }
            }
            else
                MessageBox.Show("该学生记录已存在！", "提示");
        }

        private void 学生信息_出生日期_ValueChanged(object sender, EventArgs e)
        {
            学生信息_年龄.Text = (DateTime.Now.Year - 学生信息_出生日期.Value.Year).ToString() + "岁";
        }

        private void 学生信息_修改_Click(object sender, EventArgs e)
        {
            if (学生信息_出生日期.Value.ToString().Equals("") || 学生信息_学号.Text.Trim().Equals("") ||
                学生信息_姓名.Text.Trim().Equals("") || 学生信息_性别.Text.Trim().Equals("") || 学生信息_年龄.Text.Trim().Equals("") ||
                学生信息_所在学院.Text.Trim().Equals("") || 学生信息_所在班.Text.Trim().Equals(""))
            {
                MessageBox.Show("信息不完整！", "提示");
                return;
            }
            else if (!IsNotExist_sid(学生信息_学号.Text.Trim()))
            {
                string sqlStr1 = "";
                try
                {
                    sqlStr1 = "update  Student set sid='" + 学生信息_学号.Text.Trim() + "',name='"
                         + 学生信息_姓名.Text.Trim() + "',sex='" + 学生信息_性别.Text.Trim() + "',birth='" + 学生信息_出生日期.Value.ToShortDateString().ToString().Trim()
                         + "',college='" + 学生信息_所在学院.Text.Trim() + "',class='" + 学生信息_所在班.Text.Trim() + "',phone='"
                         + 学生信息_电话.Text.ToString().Trim()
                        + "'where sid='" + 学生信息_学号.Text.Trim() + "'";
                    database.更新(sqlStr1);
                    学生信息获取();
                    MessageBox.Show("执行完成！", "提示");
                }
                catch
                {
                    MessageBox.Show("操作失败！请检查规范", "提示");
                    return;
                }
            }
            else
                MessageBox.Show("该学生不存在！", "提示");
        }

        private void 学生信息_性别选择_TextChanged(object sender, EventArgs e)
        {
            学生信息_性别.Text = 学生信息_性别选择.Text;
        }

        private void 学生信息_删除_Click(object sender, EventArgs e)
        {
            if (学生信息_学号.Text.Trim().Equals(""))
                MessageBox.Show("请输入学号！", "提示");
            else if (!IsNotExist_sid(学生信息_学号.Text.ToString().Trim()))
            {
                string sqlStr1;
                if (MessageBox.Show("确定要删除该记录吗？无法撤销！", "删除", MessageBoxButtons.YesNo, MessageBoxIcon.Question, MessageBoxDefaultButton.Button2) == DialogResult.Yes)
                {
                    try
                    {
                        sqlStr1 = "delete from Student where sid='" + 学生信息_学号.Text.Trim() + "'";
                        database.更新(sqlStr1);
                        学生信息获取();
                        MessageBox.Show("删除成功！", "提示");
                    }
                    catch
                    {
                        MessageBox.Show("操作失败！请检查规范", "提示");
                    }
                }
                else
                    return;
            }
            else
            {
                MessageBox.Show("该记录不存在！", "提示");
                return;
            }
        }

        private void 学生信息查询_Click(object sender, EventArgs e)
        {
            if (学生信息查询条件.SelectedIndex == -1 || 学生信息查询值.Text.Trim().Equals(""))
            {
                MessageBox.Show("查询条件不足！", "提示");
                return;
            }
            else
            {
                string sqlStr1 = "";
                switch (学生信息查询条件.SelectedIndex)
                {
                    case 0: { sqlStr1 = " select * from Student where sid='" + 学生信息查询值.Text.Trim() + "'"; } break;
                    case 1: { sqlStr1 = " select * from Student where name='" + 学生信息查询值.Text.Trim() + "'"; } break;
                    case 2: { sqlStr1 = " select * from Student where sex='" + 学生信息查询值.Text.Trim() + "'"; } break;
                    case 3: { sqlStr1 = " select * from Student where college='" + 学生信息查询值.Text.Trim() + "'"; } break;
                    case 4: { sqlStr1 = " select * from Student where class='" + 学生信息查询值.Text.Trim() + "'"; } break;
                }
                if (sqlStr1.Equals(""))
                {
                    MessageBox.Show("查询失败", "提示");
                    return;
                }
                try
                {
                    学生更新(sqlStr1);
                    MessageBox.Show("查询成功！", "提示");
                }
                catch
                {
                    MessageBox.Show("没有结果!", "提示");
                }
            }
        }

        private void 学生信息_取消_Click(object sender, EventArgs e)
        {
            学生信息_出生日期.Value = DateTime.Now;
            学生信息_学号.Text = "";
            学生信息_姓名.Text = "";
            学生信息_性别.Text = "";
            学生信息_年龄.Text = "";
            学生信息_所在学院.Text = "";
            学生信息_所在班.Text = "";
            学生信息_电话.Text = "";
            学生信息查询条件.SelectedIndex = -1;
            学生信息查询值.Text = "";
            学生信息获取();
        }

        private void 分配宿舍ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            分配宿舍面板.Show();
            学生信息管理界面.Hide();
            宿舍_楼.Hide();
            宿舍管理.Hide();
            调整宿舍面板.Hide();
            分配宿舍_学生信息获取();
            空余宿舍获取();
        }

        private void radioButton2_CheckedChanged(object sender, EventArgs e)
        {

        }
        
        private void 宿舍分配_男生_CheckedChanged_1(object sender, EventArgs e)
        {
            if (宿舍分配_男生.Checked == true)
            {
                string str = @"select * from Student where not exists(
                select * from StuDormitory where sid=Student.sid ) and sex='男'";
                分配宿舍_学生信息更新(str);
            }
        }

        private void 宿舍分配_女生_CheckedChanged_1(object sender, EventArgs e)
        {
            if (宿舍分配_女生.Checked == true)
            {
                string str = @"select * from Student where not exists(
                select * from StuDormitory where sid=Student.sid ) and sex='女'";
                分配宿舍_学生信息更新(str);
            }
        }

        private void 宿舍分配_全部性别_CheckedChanged(object sender, EventArgs e)
        {
            if (宿舍分配_全部性别.Checked == true)
                分配宿舍_学生信息获取();
        }

        private void dataGridView1_RowHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            int n = dataGridView1.CurrentCell.RowIndex;
            if (n < dataGridView1.RowCount)
            {
                宿舍分配_楼号显示框.Text = dataGridView1[1, n].Value.ToString().Trim();
                分配宿舍_id.Text = dataGridView1[0, n].Value.ToString().Trim();
                宿舍分配_宿舍显示框.Text = dataGridView1[2, n].Value.ToString().Trim();
            }
        }

        private void 分配宿舍_学生信息_RowHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            int n = 分配宿舍_学生信息.CurrentCell.RowIndex;
            if (n < 分配宿舍_学生信息.RowCount)
            {
                分配宿舍_学号显示框.Text = 分配宿舍_学生信息[0, n].Value.ToString().Trim();
                宿舍分配_姓名显示框.Text = 分配宿舍_学生信息[1, n].Value.ToString().Trim();
                宿舍分配_性别显示框.Text = 分配宿舍_学生信息[2, n].Value.ToString().Trim();
            }
        }

        private void 宿舍分配_分配按钮_Click(object sender, EventArgs e)
        {
            if (分配宿舍_id.Text.Equals("") || 分配宿舍_学号显示框.Text.Equals(""))
            {
                MessageBox.Show("缺少选中数据!", "提示");
                return;
            }
            else
            {
                if (!IsNotExist_OtherSex(分配宿舍_id.Text.Trim(), 分配宿舍_学号显示框.Text.Trim()))
                {
                    MessageBox.Show("该宿舍已经有其他性别的学生!", "提示");
                    return;
                }
                try
                {
                    string str2 = @"
                   insert into StuDormitory values('" + 分配宿舍_id.Text.Trim() + "','" + 分配宿舍_学号显示框.Text.Trim() + "')";
                    database.更新(str2);
                    宿舍分配_楼号显示框.Text = "";
                    分配宿舍_id.Text = "";
                    宿舍分配_宿舍显示框.Text = "";
                    分配宿舍_学号显示框.Text = "";
                    宿舍分配_姓名显示框.Text = "";
                    宿舍分配_性别显示框.Text = "";
                    分配宿舍_学生信息获取();
                    空余宿舍获取();
                    MessageBox.Show("操作完成", "提示");
                }
                catch
                {
                    MessageBox.Show("操作失败!", "提示");
                }
            }
        }

        private void 宿舍分配_查询学号_Click(object sender, EventArgs e)
        {
            if (分配宿舍_学号文本框.Text.Trim().Equals(""))
            {
                MessageBox.Show("学号不能为空!", "提示");
                return;
            }
            else
            {
                string str = @"select * from Student where not exists(
                select * from StuDormitory where sid=Student.sid ) and sid='" + 分配宿舍_学号文本框.Text.Trim() + "'";
                分配宿舍_学生信息更新(str);
            }
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void 调整宿舍ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            调整宿舍面板.Show();
            分配宿舍面板.Hide();
            学生信息管理界面.Hide();
            宿舍_楼.Hide();
            宿舍管理.Hide();
            调整表获取();
            新宿舍表获取();
        }

        private void 调整表_RowHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {

        }

        private void 调整宿舍_Click(object sender, EventArgs e)
        {
            int oldid = -1;
            if (调整宿舍_学号.Text.Trim().Equals("") || 调整_时间.Value.ToString().Equals("") || 新宿舍序号.Text.Trim().Equals(""))
            {
                MessageBox.Show("信息不全!", "提示");
                return;
            }
            else if (!IsExit_sid(调整宿舍_学号.Text.Trim()))
            {
                MessageBox.Show("学生不存在!", "提示");
                return;
            }
            else if (!IsNotchongfu_s_d(新宿舍序号.Text.Trim(), 调整宿舍_学号.Text.Trim()))
            {
                MessageBox.Show("宿舍相同!", "提示");
                return;
            }
            else
            {
                try
                {
                    DataSet ds = new DataSet();
                    ds = database.获取数据("select * from StuDormitory where sid ='" + 调整宿舍_学号.Text.Trim() + "'");
                    if (ds != null)
                        oldid = Convert.ToInt32(ds.Tables[0].Rows[0]["did"].ToString());
                    else
                    {
                        MessageBox.Show("还未分配宿舍!", "提示");
                        return;
                    }
                    if (!IsNotExist_OtherSex(新宿舍序号.Text.Trim(), 调整宿舍_学号.Text.Trim()))
                    {
                        MessageBox.Show("该宿舍已经有其他性别的学生!", "提示");
                        return;
                    }
                    database.更新("insert into Adjust values ('" + 调整宿舍_学号.Text.Trim() + "','" + 调整_时间.Value.ToShortDateString() + "','" + oldid.ToString() + "','" + 新宿舍序号
                        .Text.Trim() + "','" + 原因.Text.Trim() + "')");
                    database.更新("update  StuDormitory set did='" + 新宿舍序号.Text.Trim() + "',sid='" + 调整宿舍_学号.Text.Trim() + "'where sid='" + 调整宿舍_学号.Text.Trim() + "'");
                    调整表获取();
                    新宿舍表获取();
                }
                catch
                {
                    MessageBox.Show("操作失败，请检查数据规范！", "提示");
                    return;
                }
            }
        }

        private void 调整_时间_ValueChanged(object sender, EventArgs e)
        {

        }

        private void panel1_Paint(object sender, PaintEventArgs e)
        {

        }

        private void 新宿舍表_RowHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            int n = 新宿舍表.CurrentCell.RowIndex;
            if (n < 新宿舍表.RowCount)
                新宿舍序号.Text = 新宿舍表[0, n].Value.ToString().Trim();
        }

        private void 调整宿舍_查找_Click(object sender, EventArgs e)
        {
            if (调整宿舍_学号.Text.Trim().Equals(""))
            {
                MessageBox.Show("请输入学号!", "提示");
                return;
            }
            else if (!IsExit_sid(调整宿舍_学号.Text.Trim()))
            {
                MessageBox.Show("学生不存在!", "提示");
                return;
            }
        }
       
        private void 管理ToolStripMenuItem_Click(object sender, EventArgs e)
        {

        }

        private void 学生信息管理界面_Paint(object sender, PaintEventArgs e)
        {

        }

        private void 学生信息表_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void 宿舍查询条件_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void 宿舍信息表_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void 宿舍序号_TextChanged(object sender, EventArgs e)
        {

        }

        private void 宿舍楼号文本框_TextChanged(object sender, EventArgs e)
        {

        }

        private void 楼号下拉表_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void 宿舍号_TextChanged(object sender, EventArgs e)
        {

        }

        private void 楼层_TextChanged(object sender, EventArgs e)
        {

        }

        private void 床位数_TextChanged(object sender, EventArgs e)
        {

        }

        private void 宿舍查询值_TextChanged(object sender, EventArgs e)
        {

        }
    }
}
