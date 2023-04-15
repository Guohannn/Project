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
 ALIENTEKս��STM32������ʵ��32
 MPU6050���ᴫ���� ʵ��
 ����֧�֣�www.openedv.com
 �Ա����̣�http://eboard.taobao.com 
 ��ע΢�Ź���ƽ̨΢�źţ�"����ԭ��"����ѻ�ȡSTM32���ϡ�
 ������������ӿƼ����޹�˾  
 ���ߣ�����ԭ�� @ALIENTEK
************************************************/


//����1����1���ַ� 
//c:Ҫ���͵��ַ�
void usart1_send_char(u8 c)
{   	
	while(USART_GetFlagStatus(USART1,USART_FLAG_TC)==RESET); //ѭ������,ֱ���������   
	USART_SendData(USART1,c);  
} 
//�������ݸ�����������λ�����(V2.6�汾)
//fun:������. 0XA0~0XAF
//data:���ݻ�����,���28�ֽ�!!
//len:data����Ч���ݸ���
void usart1_niming_report(u8 fun,u8*data,u8 len)
{
	u8 send_buf[32];
	u8 i;
	if(len>28)return;	//���28�ֽ����� 
	send_buf[len+3]=0;	//У��������
	send_buf[0]=0X88;	//֡ͷ
	send_buf[1]=fun;	//������
	send_buf[2]=len;	//���ݳ���
	for(i=0;i<len;i++)send_buf[3+i]=data[i];			//��������
	for(i=0;i<len+3;i++)send_buf[len+3]+=send_buf[i];	//����У���	
	for(i=0;i<len+4;i++)usart1_send_char(send_buf[i]);	//�������ݵ�����1 
}
//���ͼ��ٶȴ��������ݺ�����������
//aacx,aacy,aacz:x,y,z������������ļ��ٶ�ֵ
//gyrox,gyroy,gyroz:x,y,z�������������������ֵ
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
	usart1_niming_report(0XA1,tbuf,12);//�Զ���֡,0XA1
}	
//ͨ������1�ϱ���������̬���ݸ�����
//aacx,aacy,aacz:x,y,z������������ļ��ٶ�ֵ
//gyrox,gyroy,gyroz:x,y,z�������������������ֵ
//roll:�����.��λ0.01�ȡ� -18000 -> 18000 ��Ӧ -180.00  ->  180.00��
//pitch:������.��λ 0.01�ȡ�-9000 - 9000 ��Ӧ -90.00 -> 90.00 ��
//yaw:�����.��λΪ0.1�� 0 -> 3600  ��Ӧ 0 -> 360.0��
void usart1_report_imu(short aacx,short aacy,short aacz,short gyrox,short gyroy,short gyroz,short roll,short pitch,short yaw)
{
	u8 tbuf[28]; 
	u8 i;
	for(i=0;i<28;i++)tbuf[i]=0;//��0
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
	usart1_niming_report(0XAF,tbuf,28);//�ɿ���ʾ֡,0XAF
}  
 	
 int main(void)
 {	 
	
	u8 t=0,report=0;			//Ĭ�Ͽ����ϱ�
	u8 key;
	float pitch,roll,yaw; 		//ŷ����
	short aacx,aacy,aacz;		//���ٶȴ�����ԭʼ����
	short gyrox,gyroy,gyroz;	//������ԭʼ����
	short temp;					//�¶�	
	 dat=1;
	NVIC_PriorityGroupConfig(NVIC_PriorityGroup_2);	 //����NVIC�жϷ���2:2λ��ռ���ȼ���2λ��Ӧ���ȼ�
	uart_init(500000);	 	//���ڳ�ʼ��Ϊ500000
	delay_init();	//��ʱ��ʼ�� 
	usmart_dev.init(72);		//��ʼ��USMART
	//LED_Init();		  			//��ʼ����LED���ӵ�Ӳ���ӿ�
	KEY_Init();					//��ʼ������
	LCD_Init();			   		//��ʼ��LCD  
	MPU_Init();					//��ʼ��MPU6050
 	

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
  POINT_COLOR=RED;			//��������Ϊ��ɫ 	
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
		
		
			
		/* ��Ϸ����*/
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
				
			temp=MPU_Get_Temperature();	//�õ��¶�ֵ
			MPU_Get_Accelerometer(&aacx,&aacy,&aacz);	//�õ����ٶȴ���������
			MPU_Get_Gyroscope(&gyrox,&gyroy,&gyroz);	//�õ�����������
			if(report)mpu6050_send_data(aacx,aacy,aacz,gyrox,gyroy,gyroz);//���Զ���֡���ͼ��ٶȺ�������ԭʼ����
			if(report)usart1_report_imu(aacx,aacy,aacz,gyrox,gyroy,gyroz,(int)(roll*100),(int)(pitch*100),(int)(yaw*10));
			if((t%10)==0)
			{ 
				
			  if(temp<0)
				{
					LCD_ShowChar(30+48,200,'-',16,0);		//��ʾ����
					temp=-temp;		//תΪ����
				}else LCD_ShowChar(30+48,200,' ',16,0);		//ȥ������ 
			
				
				
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
	
		
	


  
 


