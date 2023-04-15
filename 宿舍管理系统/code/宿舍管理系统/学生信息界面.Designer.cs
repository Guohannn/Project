namespace 学生宿舍管理系统
{
    partial class 学生信息界面
    {
        private System.ComponentModel.IContainer components = null;
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
                components.Dispose();
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码
        
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(学生信息界面));
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.查看个人信息ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.查看宿舍信息ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.查看调整记录ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.退出登录ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.信息表 = new System.Windows.Forms.DataGridView();
            this.信息面板 = new System.Windows.Forms.Panel();
            this.menuStrip1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.信息表)).BeginInit();
            this.信息面板.SuspendLayout();
            this.SuspendLayout();
            
            

            // menuStrip1
            // 
            this.menuStrip1.BackColor = System.Drawing.SystemColors.InactiveCaption;
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.查看个人信息ToolStripMenuItem,
            this.查看宿舍信息ToolStripMenuItem,
            this.查看调整记录ToolStripMenuItem,
            this.退出登录ToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Padding = new System.Windows.Forms.Padding(4, 2, 0, 2);
            this.menuStrip1.Size = new System.Drawing.Size(802, 24);
            this.menuStrip1.TabIndex = 2;
            this.menuStrip1.Text = "menuStrip1";
            this.menuStrip1.ItemClicked += new System.Windows.Forms.ToolStripItemClickedEventHandler(this.menuStrip1_ItemClicked);
            // 
            // 查看个人信息ToolStripMenuItem
            // 
            this.查看个人信息ToolStripMenuItem.BackColor = System.Drawing.SystemColors.InactiveCaption;
            this.查看个人信息ToolStripMenuItem.Font = new System.Drawing.Font("楷体", 12F);
            this.查看个人信息ToolStripMenuItem.Name = "查看个人信息ToolStripMenuItem";
            this.查看个人信息ToolStripMenuItem.Size = new System.Drawing.Size(148, 20);
            this.查看个人信息ToolStripMenuItem.Text = "查看宿舍成员信息";
            this.查看个人信息ToolStripMenuItem.Click += new System.EventHandler(this.查看个人信息ToolStripMenuItem_Click);
            // 
            // 查看宿舍信息ToolStripMenuItem
            // 
            this.查看宿舍信息ToolStripMenuItem.BackColor = System.Drawing.SystemColors.InactiveCaption;
            this.查看宿舍信息ToolStripMenuItem.Font = new System.Drawing.Font("楷体", 12F);
            this.查看宿舍信息ToolStripMenuItem.Name = "查看宿舍信息ToolStripMenuItem";
            this.查看宿舍信息ToolStripMenuItem.Size = new System.Drawing.Size(116, 20);
            this.查看宿舍信息ToolStripMenuItem.Text = "查看宿舍信息";
            this.查看宿舍信息ToolStripMenuItem.Click += new System.EventHandler(this.查看宿舍信息ToolStripMenuItem_Click);
            // 
            // 查看调整记录ToolStripMenuItem
            // 
            this.查看调整记录ToolStripMenuItem.BackColor = System.Drawing.SystemColors.InactiveCaption;
            this.查看调整记录ToolStripMenuItem.Font = new System.Drawing.Font("楷体", 12F);
            this.查看调整记录ToolStripMenuItem.Name = "查看调整记录ToolStripMenuItem";
            this.查看调整记录ToolStripMenuItem.Size = new System.Drawing.Size(116, 20);
            this.查看调整记录ToolStripMenuItem.Text = "查看调整记录";
            this.查看调整记录ToolStripMenuItem.Click += new System.EventHandler(this.查看调整记录ToolStripMenuItem_Click);
            // 
            // 退出登录ToolStripMenuItem
            // 
            this.退出登录ToolStripMenuItem.BackColor = System.Drawing.SystemColors.InactiveCaption;
            this.退出登录ToolStripMenuItem.Font = new System.Drawing.Font("楷体", 12F);
            this.退出登录ToolStripMenuItem.Name = "退出登录ToolStripMenuItem";
            this.退出登录ToolStripMenuItem.Size = new System.Drawing.Size(84, 20);
            this.退出登录ToolStripMenuItem.Text = "退出登录";
            this.退出登录ToolStripMenuItem.Click += new System.EventHandler(this.退出登录ToolStripMenuItem_Click);
            // 
            // 信息表
            // 
            this.信息表.AllowUserToAddRows = false;
            this.信息表.AllowUserToDeleteRows = false;
            this.信息表.AllowUserToOrderColumns = true;
            this.信息表.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.信息表.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.信息表.BackgroundColor = System.Drawing.SystemColors.Window;
            this.信息表.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.信息表.Location = new System.Drawing.Point(11, 15);
            this.信息表.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.信息表.Name = "信息表";
            this.信息表.ReadOnly = true;
            this.信息表.RowTemplate.Height = 27;
            this.信息表.Size = new System.Drawing.Size(780, 325);
            this.信息表.TabIndex = 0;
            this.信息表.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.信息表_CellContentClick);
            // 
            // 信息面板
            // 
            this.信息面板.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.信息面板.AutoScroll = true;
            this.信息面板.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("信息面板.BackgroundImage")));
            this.信息面板.Controls.Add(this.信息表);
            this.信息面板.Location = new System.Drawing.Point(0, 26);
            this.信息面板.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.信息面板.Name = "信息面板";
            this.信息面板.Size = new System.Drawing.Size(802, 356);
            this.信息面板.TabIndex = 1;
            this.信息面板.Paint += new System.Windows.Forms.PaintEventHandler(this.信息面板_Paint);
            // 
            // 学生信息界面
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(802, 377);
            this.Controls.Add(this.menuStrip1);
            this.Controls.Add(this.信息面板);
            this.MainMenuStrip = this.menuStrip1;
            this.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.Name = "学生信息界面";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "学生信息界面";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.学生信息界面_FormClosing);
            this.Load += new System.EventHandler(this.学生信息界面_Load);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.信息表)).EndInit();
            this.信息面板.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem 查看个人信息ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 查看宿舍信息ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 查看调整记录ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 退出登录ToolStripMenuItem;
        private System.Windows.Forms.DataGridView 信息表;
        private System.Windows.Forms.Panel 信息面板;
    }
}

