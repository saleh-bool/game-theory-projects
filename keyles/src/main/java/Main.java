using System;
        using System.Collections.Generic;
        using System.ComponentModel;
        using System.Data;
        using System.Drawing;
        using System.Linq;
        using System.Text;
        using System.Windows.Forms;
        using System.Data.SqlClient;

        namespace WindowsFormsApplication2
        {

public partial class Form1 : Form
        {
        SqlConnection connection = new SqlConnection();
        SqlCommand command = new SqlCommand();
        SqlDataReader reade;

public Form1()
        {
        InitializeComponent();
        connection.ConnectionString = "Data Source=USER4-PC;Initial Catalog=ui962;" +
        "Integrated Security=SSPI";
        command.Connection = connection;
        command.CommandText = "select s_id, name from student";
        connection.Open();
        reade = command.ExecuteReader();
        while (reade.Read())
        {
        String id = reade["s_id"].ToString();
        String name = reade["name"].ToString();
        listBox1.Items.Add(String.Format("{0},{1}", id, name));
        }
        reade.Close();
        connection.Close();
        }

private void Form1_Load(object sender, EventArgs e)
        {

        }

private void button1_Click(object sender, EventArgs e)
        {
        String id = textBox1.Text;
        String name = textBox2.Text;
        connection.ConnectionString = "Data Source=USER4-PC;Initial Catalog=ui962;" +
        "Integrated Security=SSPI";
        command.Connection = connection;
        command.CommandText = String.Format("insert into "+
        "student(s_id, name) output inserted. s_id, inserted.name " +
        "values ({0}, '{1}')", id, name);
        connection.Open();
        reade = command.ExecuteReader();
        while (reade.Read())
        {
        String id1 = reade["s_id"].ToString();
        String name1 = reade["name"].ToString();
        listBox1.Items.Add(String.Format("{0},{1}", id1, name1));
        }
        reade.Close();
        connection.Close();
        }

private void label1_Click(object sender, EventArgs e)
        {

        }

private void label2_Click(object sender, EventArgs e)
        {

        }
        }
        }
