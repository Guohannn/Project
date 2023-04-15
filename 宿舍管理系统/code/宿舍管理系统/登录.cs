using System;//主命名空间//修改完成
using System.Data;//数据命名空间
using System.Windows.Forms;//命名空间

namespace 学生宿舍管理系统
{
    public partial class 登录 : Form//创建分布类代码
    {
        public static bool exit = false;

        public 登录()
        {
            InitializeComponent();//初始化窗体组件
        }

        public static long id;

        private void 登录_Load(object sender, EventArgs e)//触发事件的控件对象，事件数据的类的基类
        {
            学生登录.Show();
            登录类型.Show();
            管理人员登录.Hide();
        }

        private void 学生单选按钮_CheckedChanged(object sender, EventArgs e)
        {
            if (学生单选按钮.Checked == true)
            {
                账号.Clear();
                管理员密码.Clear();
                学号.Clear();
                管理人员登录.Hide();
                学生登录.Show();
                学生密码.Show();
            }
        }

        private void 管理人员单选按钮_CheckedChanged(object sender, EventArgs e)
        {
            if (管理人员单选按钮.Checked == true)
            {
                账号.Clear();
                管理员密码.Clear();
                学号.Clear();
                管理人员登录.Show();
                学生登录.Hide();
                学生密码.Hide();
            }
        }

        private void Login() //登录
        {
            if (管理人员单选按钮.Checked == true) //检查输入
            {
                if (账号.Text.Trim().Equals("") || 管理员密码.Text.Trim().Equals(""))//返回去掉空格的String类后用Equals("")判断得到的字符串是否为空字符串，是则执行if语句
                {
                    if (账号.Text.Trim().Equals(""))
                        MessageBox.Show("请输入账号！", "提示");
                    else if (管理员密码.Text.Trim().Equals(""))
                        MessageBox.Show("请输入密码！", "提示");
                }
                else if (账号.Text.Length > 12)
                    MessageBox.Show("位数错误！", "提示");
                else //匹配账户
                {
                    String 数据库字符串 = "select * from Manager where mid='" + 账号.Text.Trim() + "'";
                    DataSet 数据集 = new DataSet();
                    数据集 = database.获取数据(数据库字符串);
                    if (数据集 == null)//没有匹配账户 
                        MessageBox.Show("账号不存在！", "提示");
                    else//登录成功
                    {
                        String 原密码 = 数据集.Tables[0].Rows[0]["pass"].ToString();//表示返回数据集中第一张表第一行的pass列
                        if (原密码.Trim().Equals(管理员密码.Text.Trim()))//匹配成功
                        {
                            登录.id = long.Parse(数据集.Tables[0].Rows[0]["mid"].ToString());
                            MessageBox.Show("账号" + 数据集.Tables[0].Rows[0]["mid"].ToString() + "\n" + "姓名：" + 数据集.Tables[0].Rows[0]["name"].ToString() + "\n", "登录成功");
                            new 管理界面().Show();
                            this.Close();
                        }
                        else //匹配失败
                            MessageBox.Show("密码错误！", "提示");
                    }
                }
            }
            else if (学生单选按钮.Checked == true) //学生登录
            {
                if (学号.Text.Trim().Equals("") || 学生密码.Text.Trim().Equals(""))
                {
                    if (学号.Text.Trim().Equals(""))
                        MessageBox.Show("请输入学号！", "提示");
                    else if (学生密码.Text.Trim().Equals(""))
                        MessageBox.Show("请输入密码！", "提示");
                }
                else if (学号.Text.Length > 12)
                    MessageBox.Show("位数错误！", "提示");
                else //匹配账户
                {
                    String 数据库字符串 = "select * from Student where sid='" + 学号.Text.Trim() + "'";
                    DataSet 数据集 = new DataSet();
                    数据集 = database.获取数据(数据库字符串);
                    if (数据集 == null)//没有匹配账户 
                        MessageBox.Show("学号不存在！", "提示");
                    else//登录成功
                    {
                        String 原密码 = 数据集.Tables[0].Rows[0]["pass"].ToString();
                        if (原密码.Trim().Equals(学生密码.Text.Trim()))//匹配成功
                        {
                            登录.id = long.Parse(数据集.Tables[0].Rows[0]["sid"].ToString());
                            MessageBox.Show("学号" + 数据集.Tables[0].Rows[0]["sid"].ToString() + "\n" + "姓名：" + 数据集.Tables[0].Rows[0]["name"].ToString() + "\n", "登录成功");
                            this.Close();
                            new 学生信息界面().Show();
                        }
                        else //匹配失败
                            MessageBox.Show("密码错误！", "提示");
                    }
                }
            }
        }

        private void 显示密码_CheckedChanged(object sender, EventArgs e)
        {
            if (显示密码.Checked == true)
            {
                管理员密码.UseSystemPasswordChar = false;
                学生密码.UseSystemPasswordChar = false;
            }
            else if (显示密码.Checked == false)
            {
                管理员密码.UseSystemPasswordChar = true;
                学生密码.UseSystemPasswordChar = true;
            }
        }

        private void 登录按钮_Click(object sender, EventArgs e)
        {
            Login();
        }

        private void 登录_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.Equals(Keys.Enter))
                Login();
        }

        private void 登录_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (id == 0)
            {
                this.FormClosing -= new FormClosingEventHandler(this.登录_FormClosing);
                Application.Exit();//通知所有消息泵必须终止，并且在处理了消息以后关闭所有应用程序窗口。
            }

        }

        private void Label1_Click(object sender, EventArgs e)
        {

        }

        private void 学生密码_TextChanged(object sender, EventArgs e)
        {

        }

        private void 学号_TextChanged(object sender, EventArgs e)
        {

        }

        private void 账号_TextChanged(object sender, EventArgs e)
        {

        }

        private void 管理员密码_TextChanged(object sender, EventArgs e)
        {

        }

        private void 显示密码2_CheckedChanged(object sender, EventArgs e)
        {
            
        }

        private void 学号标签_Click(object sender, EventArgs e)
        {

        }

        private void 登录类型_Enter(object sender, EventArgs e)
        {

        }

        private void 学号标签_Click_1(object sender, EventArgs e)
        {

        }

        private void 学生登录_Enter(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }
    }
}

