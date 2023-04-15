
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace 学生宿舍管理系统
{
    public partial class 主线 : Form
    {
        public 主线()
        {
            InitializeComponent();
        }

        private void 主程序_Load(object sender, EventArgs e)
        {
            new 登录().Show();
            this.Hide();
        }
    }
}
