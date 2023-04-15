using System.Data.SqlClient;
using System.Data;
using System.Windows.Forms;


namespace 学生宿舍管理系统
{
    class database //OleDbConnection
    {
        private static string connStr = @"data source=.;database = 宿舍管理系统; integrated security = true";
        private static SqlConnection conn = new SqlConnection(connStr);

        public static DataSet 获取数据(string sqlStr)
        {
            try
            {  
                try
                {
                    conn.Open();
                }
                catch 
                {
                }
                SqlDataAdapter myAdapter = new SqlDataAdapter(sqlStr, conn);
                DataSet myDataSet = new DataSet();
                myDataSet.Clear();
                myAdapter.Fill(myDataSet);
                conn.Close();
                if (myDataSet.Tables[0].Rows.Count != 0)
                {
                    return myDataSet;
                }
                else
                {
                    return null;
                }
            }
            catch
            {
                MessageBox.Show("打开数据库失败", "提示");
                return null;
            }
        }

        public static void 更新(string sqlStr)
        {
            try
            {
                try
                {
                    conn.Open();
                }
                catch 
                {
                }
            SqlCommand myCmd = new SqlCommand(sqlStr, conn);
            myCmd.CommandType = CommandType.Text;
            myCmd.ExecuteNonQuery();
            conn.Close();
            }
            catch
            {
                MessageBox.Show("更新数据库失败", "提示");
                return;
            }
        }

    }

}
