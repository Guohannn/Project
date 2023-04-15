namespace 学生宿舍管理系统
{
    partial class 登录
    {
        private System.ComponentModel.IContainer components = null;
        
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
                components.Dispose();
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(登录));
            this.登录按钮 = new System.Windows.Forms.Button();
            this.管理人员登录 = new System.Windows.Forms.GroupBox();
            this.显示密码 = new System.Windows.Forms.CheckBox();
            this.管理员账号标签 = new System.Windows.Forms.Label();
            this.管理员密码 = new System.Windows.Forms.TextBox();
            this.密码标签 = new System.Windows.Forms.Label();
            this.账号 = new System.Windows.Forms.TextBox();
            this.学生登录 = new System.Windows.Forms.GroupBox();
            this.学生密码 = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.学号 = new System.Windows.Forms.TextBox();
            this.学号标签 = new System.Windows.Forms.Label();
            this.学生单选按钮 = new System.Windows.Forms.RadioButton();
            this.管理人员单选按钮 = new System.Windows.Forms.RadioButton();
            this.登录类型 = new System.Windows.Forms.GroupBox();
            this.label2 = new System.Windows.Forms.Label();
            this.管理人员登录.SuspendLayout();
            this.学生登录.SuspendLayout();
            this.登录类型.SuspendLayout();
            this.SuspendLayout();
            // 
            // 登录按钮
            // 
            this.登录按钮.AllowDrop = true;
            this.登录按钮.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.登录按钮.BackColor = System.Drawing.Color.Transparent;
            this.登录按钮.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.登录按钮.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.登录按钮.Font = new System.Drawing.Font("楷体", 15.5F, System.Drawing.FontStyle.Bold);
            this.登录按钮.ForeColor = System.Drawing.SystemColors.Control;
            this.登录按钮.Location = new System.Drawing.Point(272, 382);
            this.登录按钮.Name = "登录按钮";
            this.登录按钮.Size = new System.Drawing.Size(122, 36);
            this.登录按钮.TabIndex = 0;
            this.登录按钮.Text = "登录";
            this.登录按钮.UseVisualStyleBackColor = false;
            this.登录按钮.Click += new System.EventHandler(this.登录按钮_Click);
            // 
            // 管理人员登录
            // 
            this.管理人员登录.BackColor = System.Drawing.Color.Transparent;
            this.管理人员登录.Controls.Add(this.管理员账号标签);
            this.管理人员登录.Controls.Add(this.管理员密码);
            this.管理人员登录.Controls.Add(this.密码标签);
            this.管理人员登录.Controls.Add(this.账号);
            this.管理人员登录.Font = new System.Drawing.Font("楷体", 14.25F, System.Drawing.FontStyle.Bold);
            this.管理人员登录.ForeColor = System.Drawing.Color.White;
            this.管理人员登录.Location = new System.Drawing.Point(307, 180);
            this.管理人员登录.Name = "管理人员登录";
            this.管理人员登录.Size = new System.Drawing.Size(270, 160);
            this.管理人员登录.TabIndex = 1;
            this.管理人员登录.TabStop = false;
            this.管理人员登录.Text = "管理人员登录";
            // 
            // 显示密码
            // 
            this.显示密码.AutoSize = true;
            this.显示密码.Font = new System.Drawing.Font("楷体", 14.25F);
            this.显示密码.Location = new System.Drawing.Point(44, 122);
            this.显示密码.Name = "显示密码";
            this.显示密码.Size = new System.Drawing.Size(108, 23);
            this.显示密码.TabIndex = 5;
            this.显示密码.Text = "显示密码";
            this.显示密码.UseVisualStyleBackColor = true;
            this.显示密码.UseWaitCursor = true;
            this.显示密码.CheckedChanged += new System.EventHandler(this.显示密码_CheckedChanged);
            // 
            // 管理员账号标签
            // 
            this.管理员账号标签.AutoSize = true;
            this.管理员账号标签.Font = new System.Drawing.Font("楷体", 12.5F, System.Drawing.FontStyle.Bold);
            this.管理员账号标签.ForeColor = System.Drawing.SystemColors.Control;
            this.管理员账号标签.Location = new System.Drawing.Point(19, 59);
            this.管理员账号标签.Name = "管理员账号标签";
            this.管理员账号标签.Size = new System.Drawing.Size(46, 17);
            this.管理员账号标签.TabIndex = 1;
            this.管理员账号标签.Text = "账号";
            // 
            // 管理员密码
            // 
            this.管理员密码.Font = new System.Drawing.Font("楷体", 14.25F, System.Drawing.FontStyle.Bold);
            this.管理员密码.Location = new System.Drawing.Point(74, 95);
            this.管理员密码.Name = "管理员密码";
            this.管理员密码.Size = new System.Drawing.Size(181, 29);
            this.管理员密码.TabIndex = 3;
            this.管理员密码.UseSystemPasswordChar = true;
            // 
            // 密码标签
            // 
            this.密码标签.AutoSize = true;
            this.密码标签.Font = new System.Drawing.Font("楷体", 12.5F, System.Drawing.FontStyle.Bold);
            this.密码标签.ForeColor = System.Drawing.SystemColors.Control;
            this.密码标签.Location = new System.Drawing.Point(19, 101);
            this.密码标签.Name = "密码标签";
            this.密码标签.Size = new System.Drawing.Size(46, 17);
            this.密码标签.TabIndex = 2;
            this.密码标签.Text = "密码";
            // 
            // 账号
            // 
            this.账号.Font = new System.Drawing.Font("楷体", 14.25F, System.Drawing.FontStyle.Bold);
            this.账号.Location = new System.Drawing.Point(74, 53);
            this.账号.Name = "账号";
            this.账号.Size = new System.Drawing.Size(181, 29);
            this.账号.TabIndex = 0;
            // 
            // 学生登录
            // 
            this.学生登录.BackColor = System.Drawing.Color.Transparent;
            this.学生登录.Controls.Add(this.学生密码);
            this.学生登录.Controls.Add(this.label1);
            this.学生登录.Controls.Add(this.学号);
            this.学生登录.Controls.Add(this.学号标签);
            this.学生登录.Font = new System.Drawing.Font("楷体", 14.25F, System.Drawing.FontStyle.Bold);
            this.学生登录.ForeColor = System.Drawing.SystemColors.Control;
            this.学生登录.Location = new System.Drawing.Point(307, 180);
            this.学生登录.Name = "学生登录";
            this.学生登录.Size = new System.Drawing.Size(270, 160);
            this.学生登录.TabIndex = 2;
            this.学生登录.TabStop = false;
            this.学生登录.Text = "学生登录";
            // 
            // 学生密码
            // 
            this.学生密码.Location = new System.Drawing.Point(74, 95);
            this.学生密码.Name = "学生密码";
            this.学生密码.Size = new System.Drawing.Size(181, 29);
            this.学生密码.TabIndex = 5;
            this.学生密码.UseSystemPasswordChar = true;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("楷体", 12.75F, System.Drawing.FontStyle.Bold);
            this.label1.Location = new System.Drawing.Point(19, 101);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(46, 17);
            this.label1.TabIndex = 4;
            this.label1.Text = "密码";
            // 
            // 学号
            // 
            this.学号.Location = new System.Drawing.Point(74, 53);
            this.学号.Name = "学号";
            this.学号.Size = new System.Drawing.Size(181, 29);
            this.学号.TabIndex = 3;
            // 
            // 学号标签
            // 
            this.学号标签.AutoSize = true;
            this.学号标签.Font = new System.Drawing.Font("楷体", 12.75F, System.Drawing.FontStyle.Bold);
            this.学号标签.Location = new System.Drawing.Point(19, 59);
            this.学号标签.Name = "学号标签";
            this.学号标签.Size = new System.Drawing.Size(46, 17);
            this.学号标签.TabIndex = 2;
            this.学号标签.Text = "学号";
            // 
            // 学生单选按钮
            // 
            this.学生单选按钮.AutoSize = true;
            this.学生单选按钮.BackColor = System.Drawing.Color.Transparent;
            this.学生单选按钮.Checked = true;
            this.学生单选按钮.Font = new System.Drawing.Font("楷体", 11.25F, System.Drawing.FontStyle.Bold);
            this.学生单选按钮.ForeColor = System.Drawing.Color.White;
            this.学生单选按钮.Location = new System.Drawing.Point(21, 42);
            this.学生单选按钮.Name = "学生单选按钮";
            this.学生单选按钮.Size = new System.Drawing.Size(93, 19);
            this.学生单选按钮.TabIndex = 5;
            this.学生单选按钮.TabStop = true;
            this.学生单选按钮.Text = "学生登录";
            this.学生单选按钮.UseVisualStyleBackColor = false;
            this.学生单选按钮.CheckedChanged += new System.EventHandler(this.学生单选按钮_CheckedChanged);
            // 
            // 管理人员单选按钮
            // 
            this.管理人员单选按钮.AutoSize = true;
            this.管理人员单选按钮.BackColor = System.Drawing.Color.Transparent;
            this.管理人员单选按钮.Font = new System.Drawing.Font("楷体", 11.25F, System.Drawing.FontStyle.Bold);
            this.管理人员单选按钮.ForeColor = System.Drawing.Color.White;
            this.管理人员单选按钮.Location = new System.Drawing.Point(21, 84);
            this.管理人员单选按钮.Name = "管理人员单选按钮";
            this.管理人员单选按钮.Size = new System.Drawing.Size(127, 19);
            this.管理人员单选按钮.TabIndex = 6;
            this.管理人员单选按钮.Text = "管理人员登录";
            this.管理人员单选按钮.UseVisualStyleBackColor = false;
            this.管理人员单选按钮.CheckedChanged += new System.EventHandler(this.管理人员单选按钮_CheckedChanged);
            // 
            // 登录类型
            // 
            this.登录类型.BackColor = System.Drawing.Color.Transparent;
            this.登录类型.Controls.Add(this.显示密码);
            this.登录类型.Controls.Add(this.学生单选按钮);
            this.登录类型.Controls.Add(this.管理人员单选按钮);
            this.登录类型.Font = new System.Drawing.Font("楷体", 14.25F, System.Drawing.FontStyle.Bold);
            this.登录类型.ForeColor = System.Drawing.SystemColors.Control;
            this.登录类型.Location = new System.Drawing.Point(114, 180);
            this.登录类型.Name = "登录类型";
            this.登录类型.Size = new System.Drawing.Size(162, 160);
            this.登录类型.TabIndex = 7;
            this.登录类型.TabStop = false;
            this.登录类型.Text = "登录类型";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.BackColor = System.Drawing.Color.Transparent;
            this.label2.Font = new System.Drawing.Font("楷体", 30F, System.Drawing.FontStyle.Bold);
            this.label2.ForeColor = System.Drawing.SystemColors.Control;
            this.label2.Location = new System.Drawing.Point(161, 95);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(345, 40);
            this.label2.TabIndex = 9;
            this.label2.Text = "学生宿舍管理系统";
            this.label2.Click += new System.EventHandler(this.label2_Click);
            // 
            // 登录
            // 
            this.AcceptButton = this.登录按钮;
            this.AllowDrop = true;
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
            this.AutoSize = true;
            this.AutoValidate = System.Windows.Forms.AutoValidate.Disable;
            this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
            this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.ClientSize = new System.Drawing.Size(647, 511);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.登录类型);
            this.Controls.Add(this.学生登录);
            this.Controls.Add(this.登录按钮);
            this.Controls.Add(this.管理人员登录);
            this.DoubleBuffered = true;
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.Name = "登录";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "登录";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.登录_FormClosing);
            this.Load += new System.EventHandler(this.登录_Load);
            this.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.登录_KeyPress);
            this.管理人员登录.ResumeLayout(false);
            this.管理人员登录.PerformLayout();
            this.学生登录.ResumeLayout(false);
            this.学生登录.PerformLayout();
            this.登录类型.ResumeLayout(false);
            this.登录类型.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        #endregion

        private System.Windows.Forms.Button 登录按钮;
        private System.Windows.Forms.GroupBox 管理人员登录;
        private System.Windows.Forms.TextBox 管理员密码;
        private System.Windows.Forms.Label 密码标签;
        private System.Windows.Forms.Label 管理员账号标签;
        private System.Windows.Forms.GroupBox 学生登录;
        private System.Windows.Forms.TextBox 学号;
        private System.Windows.Forms.Label 学号标签;
        private System.Windows.Forms.RadioButton 学生单选按钮;
        private System.Windows.Forms.RadioButton 管理人员单选按钮;
        private System.Windows.Forms.GroupBox 登录类型;
        private System.Windows.Forms.CheckBox 显示密码;
        private System.Windows.Forms.TextBox 学生密码;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox 账号;
    }
}