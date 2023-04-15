using System;
using System.Data;
using System.Windows.Forms;

namespace 学生宿舍管理系统
{
    public partial class 学生信息界面 : Form
    {
        public static bool exit = true;
        public 学生信息界面()
        {
            InitializeComponent();
        }

        private void 学生信息界面_Load(object sender, EventArgs e)
        {
            DataSet ds = new DataSet();
            DataTable dt = new DataTable();
            ds = database.获取数据("select * from Student where sid='" + 登录.id.ToString() + "'");
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
                信息表.DataSource = dt;
                信息表.Columns["sid"].HeaderText = "学号";
                信息表.Columns["name"].HeaderText = "姓名";
                信息表.Columns["sex"].HeaderText = "性别";
                信息表.Columns["college"].HeaderText = "学院";
                信息表.Columns["class"].HeaderText = "班级";
                信息表.Columns["phone"].HeaderText = "电话";
                信息表.Columns["year"].HeaderText = "年龄";
                信息表.Columns["year"].DisplayIndex = 3;

            }
            else
                信息表.DataSource = null;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            new 登录().Show();
        }
        private void 学生信息界面_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (exit)
            {
                this.FormClosing -= new FormClosingEventHandler(this.学生信息界面_FormClosing);
                Application.Exit();
            }
        }

        private void 查看个人信息ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            DataSet ds = new DataSet();
            DataTable dt = new DataTable();
            ds = database.获取数据
                (@"select *from Student where sid in
                 (select sid from StuDormitory where did in
                    (select did from  StuDormitory
                    where StuDormitory.sid='" + 登录.id.ToString() + "'))"
                 );
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
                信息表.DataSource = dt;
                信息表.Columns["sid"].HeaderText = "学号";
                信息表.Columns["name"].HeaderText = "姓名";
                信息表.Columns["sex"].HeaderText = "性别";
                信息表.Columns["college"].HeaderText = "学院";
                信息表.Columns["class"].HeaderText = "班级";
                信息表.Columns["phone"].HeaderText = "电话";
                信息表.Columns["year"].HeaderText = "年龄";
                信息表.Columns["year"].DisplayIndex = 3;
            }
            else
                信息表.DataSource = null;
        }

        private void menuStrip1_ItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {

        }

        private void 查看宿舍信息ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            DataSet ds = new DataSet();
            ds = database.获取数据
                (@"
                select Dormitory.bid as '宿舍楼',
                dnum as '宿舍号',
                floor as '楼层',
                bed as '床位数'
                from  Dormitory,Building
                where Dormitory.bid= Building.bid and  Dormitory.did =
                (select did from StuDormitory where sid='" + 登录.id.ToString() + "'" + ")"
                );

            if (ds!= null)
                信息表.DataSource = ds.Tables[0];
            else
                信息表.DataSource = null;
        }

        private void 查看调整记录ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            DataSet ds = new DataSet();
            ds = database.获取数据
                (@"
                select B1.bid as '原宿舍楼', 
                D1.dnum aS '原宿舍号',
                B2.bid as '后宿舍楼',
                D2.dnum aS '后宿舍号',
                reason as '调整原因'
                from Dormitory D1,Dormitory D2,Adjust,Building B1,Building B2
                where B1.bid=D1.bid and D1.did=Adjust.olddid and B2.bid=D2.bid and D2.did=Adjust.newdid and sid=
                '" + 登录.id.ToString() + "'"
                );
            if (ds != null)
                信息表.DataSource = ds.Tables[0];
            else
                信息表.DataSource = null;
        }

        private void 退出登录ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            exit = false;
            this.Close();
            登录.id = 0;
            new 登录().Show();
        }

        private void 信息面板_Paint(object sender, PaintEventArgs e)
        {

        }

        private void 信息表_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }
    }
}
