#include "delay.h"
#include "key.h"
#include "sys.h"
#include "lcd.h"
#include "usart.h"
#include "mpu6050.h"
#include "timer.h"
#include "snack.h" 

#include "usmart.h"   
#include "inv_mpu.h"
#include "inv_mpu_dmp_motion_driver.h" 

extern int timecount;
extern int tempLength;
extern int dat;
int coun=0;
int guangka=1;
/************************************************
 ALIENTEK战舰STM32开发板实验32
 MPU6050六轴传感器 实验
 技术支持：www.openedv.com
 淘宝店铺：http://eboard.taobao.com 
 关注微信公众平台微信号："正点原子"，免费获取STM32资料。
 广州市星翼电子科技有限公司  
 作者：正点原子 @ALIENTEK
************************************************/


//串口1发送1个字符 
//c:要发送的字符
void usart1_send_char(u8 c)
{   	
	while(USART_GetFlagStatus(USART1,USART_FLAG_TC)==RESET); //循环发送,直到发送完毕   
	USART_SendData(USART1,c);  
} 
//传送数据给匿名四轴上位机软件(V2.6版本)
//fun:功能字. 0XA0~0XAF
//data:数据缓存区,最多28字节!!
//len:data区有效数据个数
void usart1_niming_report(u8 fun,u8*data,u8 len)
{
	u8 send_buf[32];
	u8 i;
	if(len>28)return;	//最多28字节数据 
	send_buf[len+3]=0;	//校验数置零
	send_buf[0]=0X88;	//帧头
	send_buf[1]=fun;	//功能字
	send_buf[2]=len;	//数据长度
	for(i=0;i<len;i++)send_buf[3+i]=data[i];			//复制数据
	for(i=0;i<len+3;i++)send_buf[len+3]+=send_buf[i];	//计算校验和	
	for(i=0;i<len+4;i++)usart1_send_char(send_buf[i]);	//发送数据到串口1 
}
//发送加速度传感器数据和陀螺仪数据
//aacx,aacy,aacz:x,y,z三个方向上面的加速度值
//gyrox,gyroy,gyroz:x,y,z三个方向上面的陀螺仪值
void mpu6050_send_data(short aacx,short aacy,short aacz,short gyrox,short gyroy,short gyroz)
{
	u8 tbuf[12]; 
	tbuf[0]=(aacx>>8)&0XFF;
	tbuf[1]=aacx&0XFF;
	tbuf[2]=(aacy>>8)&0XFF;
	tbuf[3]=aacy&0XFF;
	tbuf[4]=(aacz>>8)&0XFF;
	tbuf[5]=aacz&0XFF; 
	tbuf[6]=(gyrox>>8)&0XFF;
	tbuf[7]=gyrox&0XFF;
	tbuf[8]=(gyroy>>8)&0XFF;
	tbuf[9]=gyroy&0XFF;
	tbuf[10]=(gyroz>>8)&0XFF;
	tbuf[11]=gyroz&0XFF;
	usart1_niming_report(0XA1,tbuf,12);//自定义帧,0XA1
}	
//通过串口1上报结算后的姿态数据给电脑
//aacx,aacy,aacz:x,y,z三个方向上面的加速度值
//gyrox,gyroy,gyroz:x,y,z三个方向上面的陀螺仪值
//roll:横滚角.单位0.01度。 -18000 -> 18000 对应 -180.00  ->  180.00度
//pitch:俯仰角.单位 0.01度。-9000 - 9000 对应 -90.00 -> 90.00 度
//yaw:航向角.单位为0.1度 0 -> 3600  对应 0 -> 360.0度
void usart1_report_imu(short aacx,short aacy,short aacz,short gyrox,short gyroy,short gyroz,short roll,short pitch,short yaw)
{
	u8 tbuf[28]; 
	u8 i;
	for(i=0;i<28;i++)tbuf[i]=0;//清0
	tbuf[0]=(aacx>>8)&0XFF;
	tbuf[1]=aacx&0XFF;
	tbuf[2]=(aacy>>8)&0XFF;
	tbuf[3]=aacy&0XFF;
	tbuf[4]=(aacz>>8)&0XFF;
	tbuf[5]=aacz&0XFF; 
	tbuf[6]=(gyrox>>8)&0XFF;
	tbuf[7]=gyrox&0XFF;
	tbuf[8]=(gyroy>>8)&0XFF;
	tbuf[9]=gyroy&0XFF;
	tbuf[10]=(gyroz>>8)&0XFF;
	tbuf[11]=gyroz&0XFF;	
	tbuf[18]=(roll>>8)&0XFF;
	tbuf[19]=roll&0XFF;
	tbuf[20]=(pitch>>8)&0XFF;
	tbuf[21]=pitch&0XFF;
	tbuf[22]=(yaw>>8)&0XFF;
	tbuf[23]=yaw&0XFF;
	usart1_niming_report(0XAF,tbuf,28);//飞控显示帧,0XAF
}  
 	
 int main(void)
 {	 
	
	u8 t=0,report=0;			//默认开启上报
	u8 key;
	float pitch,roll,yaw; 		//欧拉角
	short aacx,aacy,aacz;		//加速度传感器原始数据
	short gyrox,gyroy,gyroz;	//陀螺仪原始数据
	short temp;					//温度	
	 dat=1;
	NVIC_PriorityGroupConfig(NVIC_PriorityGroup_2);	 //设置NVIC中断分组2:2位抢占优先级，2位响应优先级
	uart_init(500000);	 	//串口初始化为500000
	delay_init();	//延时初始化 
	usmart_dev.init(72);		//初始化USMART
	//LED_Init();		  			//初始化与LED连接的硬件接口
	KEY_Init();					//初始化按键
	LCD_Init();			   		//初始化LCD  
	MPU_Init();					//初始化MPU6050
 	

	key=KEY_Scan(0);
	LCD_ShowString(110,200,400,24,24,"WELCOME TO THE SNAKE!");
	LCD_ShowString(100,250,400,24,24,"ARE YOU READY TO PLAY?");
		LCD_ShowString(100,400,400,24,24,"PRESS KEY1 TO START!");
	
	while(key!=KEY1_PRES)
	{
		report=!report;
		if(report)
		{
			key=KEY_Scan(0);
		}
	}
	while(mpu_dmp_init())
 	{
		
		delay_ms(200);
		LCD_Fill(30,130,239,130+16,WHITE);
 		delay_ms(200); 
	}
  POINT_COLOR=RED;			//设置字体为红色 	
	delay_ms(3000);
	timecount=450;
	GAME_state=0;
 	while(1)
	{
		if(GAME_state==5)
				{
					LCD_Clear(WHITE); 
					guangka++;
					tempLength=tempLength+2;
					
					LCD_ShowNum(214,380,guangka,3,16);//jinrudi  guang
					delay_ms(5000);
					GAME_state=0;
				
					continue;
				}
		if(timecount/10==0)
		{
			timecount=450;
			GAME_state=2;
			Game_over();
		}
		
		
			
		/* 游戏部分*/
				if(clear_flag)
				{
					clear_flag=0;
					LCD_Clear(WHITE); 
				}
				if(GAME_state==0)
				{					
					SNACK_L=1;
					Game_start();
					SNACK_init();
					TIM3_Int_Init(1000,7200);//10ms  
				}
				
				if(GAME_state==1) 
				{
					if(coun%100==0)
					{
					 SNACK();
					}
					if(mpu_dmp_get_data(&pitch,&roll,&yaw)==0)
		    { 
				
			temp=MPU_Get_Temperature();	//得到温度值
			MPU_Get_Accelerometer(&aacx,&aacy,&aacz);	//得到加速度传感器数据
			MPU_Get_Gyroscope(&gyrox,&gyroy,&gyroz);	//得到陀螺仪数据
			if(report)mpu6050_send_data(aacx,aacy,aacz,gyrox,gyroy,gyroz);//用自定义帧发送加速度和陀螺仪原始数据
			if(report)usart1_report_imu(aacx,aacy,aacz,gyrox,gyroy,gyroz,(int)(roll*100),(int)(pitch*100),(int)(yaw*10));
			if((t%10)==0)
			{ 
				
			  if(temp<0)
				{
					LCD_ShowChar(30+48,200,'-',16,0);		//显示负号
					temp=-temp;		//转为正数
				}else LCD_ShowChar(30+48,200,' ',16,0);		//去掉负号 
			
				
				
				temp=pitch;
				if(temp>=15)
				{
					dat=2;//up
					
				}
				if(temp<=-15)
				{
					dat=1;//dowm
				
				}					
				
				temp=roll;
				if(temp<=-15)
				{
					dat=3;//right
				
				}
				if(temp>=15)
				{
					dat=4;//left
					
				}
				t=0;
				
			switch(dat)
			{
				case 4:if(DIR==LEFT)	break;
							 else	{DIR=RIGHT;break;}	
							 
				case 1:if(DIR==UP)	break;
							 else {DIR=DOWN;break;}
							 
				case 3:if(DIR==RIGHT)	break;
							else {DIR=LEFT;break;}

				case 2:if(DIR==DOWN)break;
							else {DIR=UP;break;}
				default:break;	
			}
			
		}
			t++;
				}
				if(GAME_state==2) 
				{
					Game_over();
					LCD_ShowString(100,400,400,24,24,"PRESS KEY1 TO CONTINUE!");	
					key=KEY_Scan(0);
					while(key!=KEY1_PRES)
					{
						report=!report;
						if(report)
						{
					
						
							key=KEY_Scan(0);
						}
					}
					timecount=450;
					tempLength=5;
					dat=1;
					guangka=1;
					
				}
			
			
		if(dat==4 && GAME_state==0)
		{
			GAME_state=1;
			clear_flag=1;
			SNACK_init();	
		}
		
		if(dat && GAME_state==2)
		{
			GAME_state=0;
			LCD_Clear(WHITE); 
			clear_flag=1;
		}
	}
				coun++;
	   if(timecount>0&&SNACK_L>=tempLength)
		 {
			 GAME_state=5;
			 dat=1;
		 }
			}
}
	
		
	


  
 


