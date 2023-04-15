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


u8 DIR=2;	//方向
u8 SNACK_L=3;	//蛇长
u8 GAME_state=0;//游戏进程
u16 GAME_time;//游戏进程时间

u8 Food_flag=1; 
u8 clear_flag; 
u16 Food_x;
u16 Food_y;

u16 x1=0,x2=480,x3=0,x4=480;
u16 y1=0,y2=0,y3=600,y4=600;

u16 SNACK_x[100]={0};
u16 SNACK_y[100]={0};

u8 key;
u8 Long_key;
u8 pause_flag=0;
u16 speed=200;
short p,r,y;
int dat;
int count;
void init()
{
	NVIC_PriorityGroupConfig(NVIC_PriorityGroup_2);	 //设置NVIC中断分组2:2位抢占优先级，2位响应优先级
	
	
	uart_init(500000);	 	//串口初始化为500000
	delay_init();	//延时初始化 
	usmart_dev.init(72);		//初始化USMART
	KEY_Init();					//初始化按键
	LCD_Init();			   		//初始化LCD  
	MPU_Init();					//初始化MPU6050
	
}

void SNACK_init(void)//初始化蛇身
{
	u8 i;
	
	LCD_Clear(WHITE); 
	
	i=100;
	while(i)
	{	
		i--;
		SNACK_x[i]=0;
		SNACK_y[i]=0;
	}
	SNACK_L=3;
	GAME_time=0;
	DIR=RIGHT;
	speed=200;
	Food_flag=1;
	SNACK_x[0]=90,SNACK_y[0]=50;
	SNACK_x[1]=80,SNACK_y[1]=50;
	SNACK_x[2]=70,SNACK_y[2]=50;
	timecount=450;
	for(i=0;i<3;i++)
	{
	LCD_Fill(SNACK_x[i],SNACK_y[i],SNACK_x[i]+10,SNACK_y[i]+10,RED);
		
	if(SNACK_x[i]<SNACK_x[i+1])	
	LCD_Fill(SNACK_x[i]+8,SNACK_y[i],SNACK_x[i]+10,SNACK_y[i]+10,BLACK);
	
	else if(SNACK_x[i]>SNACK_x[i+1])	
	LCD_Fill(SNACK_x[i],SNACK_y[i],SNACK_x[i]+2,SNACK_y[i]+10,BLACK);
	
	else if(SNACK_y[i]<SNACK_y[i+1])	
	LCD_Fill(SNACK_x[i],SNACK_y[i]+8,SNACK_x[i]+10,SNACK_y[i]+10,BLACK);
	
	else if(SNACK_y[i]>SNACK_y[i+1])	
	LCD_Fill(SNACK_x[i],SNACK_y[i],SNACK_x[i]+10,SNACK_y[i]+2,BLACK);
	}
}

void SNACK(void)
{
	u8 i;
	
	LCD_Fill(SNACK_x[SNACK_L-1],SNACK_y[SNACK_L-1],SNACK_x[SNACK_L-1]+10,SNACK_y[SNACK_L-1]+10,WHITE); //防止屏闪

	if(pause_flag==0)
	{
		for(i=SNACK_L-1;i>0;i--)//记录蛇身
		{
			SNACK_x[i]=SNACK_x[i-1];
			SNACK_y[i]=SNACK_y[i-1];
		}
		//计算蛇头位置
		if(DIR==RIGHT)	SNACK_x[0]=SNACK_x[0]+10;
		if(DIR==LEFT)		SNACK_x[0]=SNACK_x[0]-10;
		if(DIR==UP)			SNACK_y[0]=SNACK_y[0]-10;
		if(DIR==DOWN)		SNACK_y[0]=SNACK_y[0]+10;
	}
	
	if(SNACK_x[0]==0 || SNACK_x[0]==470 || SNACK_y[0]==0 || SNACK_y[0]==590)
	{
		GAME_state=2;//GAME OVER
		LCD_Clear(WHITE); 
		return;
	}

	if(Food_x==SNACK_x[i] && Food_y==SNACK_y[i])//判断是否捕获到食物
	{
		Food_flag=1;
		SNACK_L++;
		
	}
	Creat_food();//判断是否产生食物
	
	LCD_Fill(SNACK_x[0],SNACK_y[0],SNACK_x[0]+10,SNACK_y[0]+10,BLACK);//画蛇头
	for(i=1;i<SNACK_L;i++)//画蛇身
	{
	LCD_Fill(SNACK_x[i],SNACK_y[i],SNACK_x[i]+10,SNACK_y[i]+10,RED);
	if(i==SNACK_L-1) break;
	if(SNACK_x[i]<SNACK_x[i+1])	
	LCD_Fill(SNACK_x[i]+8,SNACK_y[i],SNACK_x[i]+10,SNACK_y[i]+10,BLACK);
	
	else if(SNACK_x[i]>SNACK_x[i+1])	
	LCD_Fill(SNACK_x[i],SNACK_y[i],SNACK_x[i]+2,SNACK_y[i]+10,BLACK);
	
	else if(SNACK_y[i]<SNACK_y[i+1])	
	LCD_Fill(SNACK_x[i],SNACK_y[i]+8,SNACK_x[i]+10,SNACK_y[i]+10,BLACK);
	
	else if(SNACK_y[i]>SNACK_y[i+1])	
	LCD_Fill(SNACK_x[i],SNACK_y[i],SNACK_x[i]+10,SNACK_y[i]+2,BLACK);
	}
	show_map();

	for(i=1;i<SNACK_L;i++)//判断是否咬到蛇身
	{
		if(SNACK_x[0]==SNACK_x[i] && SNACK_y[0]==SNACK_y[i])
		{
			GAME_state=2;//GAME OVER
			LCD_Clear(WHITE); 
		}
	}
}

void Creat_food(void)
{

	rand();      //产生一个30到50的随机数
	if(Food_flag==1)
	{
		Food_flag=0;
		Food_x= (rand()%45+1)*10;      //产生一个1到45的随机数
		Food_y= (rand()%55+1)*10;      //产生一个1到55的随机数
		
		
	}
	LCD_Fill(Food_x,Food_y,Food_x+10,Food_y+10,RED);
}


void show_map(void)
{
	LCD_Fill(x1,y1,x2,y1+10,BLUE);	
	LCD_Fill(x1,y1,x1+10,y3,BLUE);		
	LCD_Fill(x4-10,y2,x4,y4,BLUE);		
	LCD_Fill(x3,y4-10,x4,y4,BLUE);
	LCD_Fill(210,700,480,700,WHITE);
	
	
}

void Game_start(void)
{
	
	GAME_state=1;
	timecount=45;
}


void Game_over(void)
{
	u8 str1[20];
	u8 str2[20];
	u8 str3[20];

	LCD_Clear(WHITE);

	LCD_ShowString(170,320,200,24,24,(u8 *)"YOU LOSE!");
	LCD_ShowString(165,360,200,24,24,(u8 *)"GAME OVER!");
	// LCD_ShowString(165,360,200,24,24,(u8 *)"GAME OVER!");
	//LCD_ShowNum(240,690,timecount/10,3,16);
		//LCD_ShowNum(240,710,SNACK_L,3,16);
	delay_ms(5000);
	//LCD_ShowString(185,200,200,24,24,str1);	
	//LCD_ShowString(155,250,200,24,24,str3);	
	//LCD_ShowString(150,300,200,24,24,str2);	
	LCD_ShowString(100,400,400,24,24,"PRESS KEY1 TO CONTINUE!");	
	delay_ms(5000);
}

