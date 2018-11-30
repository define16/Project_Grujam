package com.example.user.glujam;

import android.util.Log;

import java.util.ArrayList;

public class Data {

	String nowTime;
	String LastTime;
	int getTime;

	boolean sleepFlag;//���鿡�� ���, ��󿡼� ����
	
	int sleepNwake; // 0. ����  1. ���  2. ������

	ArrayList<DataDto1> values;
	ArrayList<DataDto> Time1;


	public Data(ArrayList<DataDto1> values) {
		LastTime = "00:00:00";
		Log.e("DDDDDDDDDDD", "쓰레드 데이터 처리부");
		Time1 = new ArrayList<DataDto>();
		sleepFlag = false;
		int cnt2 = 0; // ��������



		for(int i = 0; i<values.size();i++) {
//			System.out.println(i + "��° ");
//			System.out.println("Date : " + values.get(i).getDate());
//			System.out.println("Time : " + values.get(i).getTime());


			//ī�޶�
			if(values.get(i).getDatatype() ==  1) {
				//System.out.println("Type : ī�޶�");
				//System.out.println("Type : " + values.get(i).getDatatype());
				sleepNwake = (int) values.get(i).getValue();

				sleep(values.get(i).getDate(), sleepNwake, values.get(i).getTime(), Time1);

			}

			//��������
			else if(values.get(i).getDatatype() == 2) {

//				System.out.println("Type : ��������");
//				System.out.println("Type : " + values.get(i).getDatatype());
				if( values.get(i).getValue() > 80.00) {
					if(cnt2>1) {
						System.out.println("�Ͼ");
						cnt2 = 0;
						sleepNwake = 1;
						sleep(values.get(i).getDate(), sleepNwake, values.get(i).getTime(), Time1);
					}
					cnt2++;
				}
//				System.out.println("count : " + cnt2);
//				System.out.println("Value : " + values.get(i).getValue());

			}

			//�з¼���
			else if(values.get(i).getDatatype() == 3) {
//				System.out.println("Type : �з¼���");
//				System.out.println("Type : " + values.get(i).getDatatype());

				if( values.get(i).getValue() < 80.f) {
//					System.out.println("������");
				}

//				System.out.println("Value : " + values.get(i).getValue());
			}


			//�Ҹ�����
			else if(values.get(i).getDatatype() == 4) {
//				System.out.println("Type : �Ҹ�����");
//				System.out.println("Type : " + values.get(i).getDatatype());

				if( values.get(i).getValue() > 80.f) {
					System.out.println("�Ͼ");
					sleepNwake = 1;

					sleep(values.get(i).getDate(), sleepNwake, values.get(i).getTime(), Time1);

				}
//				System.out.println("Value : " + values.get(i).getValue());
			}

			//����
			else if(values.get(i).getDatatype() == 5) {
//				System.out.println("Type : ����");
//				System.out.println("Type : " + values.get(i).getDatatype());
//
//				System.out.println("Value : " + values.get(i).getValue());


			}

			//����
			else if(values.get(i).getDatatype() == 6) {
//				System.out.println("Type : ����");
//				System.out.println("Type : " + values.get(i).getDatatype());
//				System.out.println("Value : " + values.get(i).getValue());
			}

			//���� �����
			else {
//				System.out.println("Type : ���� ����ð�");
//				 // ���� ���� �ð�
//				System.out.println("Value : " + d.totalSleep(Time1));
			}


			//
		}

		for(int i = 0; i<Time1.size();i++) {
			System.out.println("Time1�� ���� : " + Time1.get(i).toString());
		}
	}


	public ArrayList<DataDto1> getValues() {
		return values;
	}

	public void setValues(ArrayList<DataDto1> values) {
		this.values = values;
	}


	public ArrayList<DataDto> getTime1() {
		return Time1;
	}

	public void setTime1(ArrayList<DataDto> time1) {
		Time1 = time1;
	}



	public int gapTime(String LastTime, String NowTime) {
		String[] split1 = LastTime.split(":");
		String[] split2 = NowTime.split(":");

		int lost_hour= Integer.parseInt(split1[0]);
		int lost_min = Integer.parseInt(split1[1]);
		int lost_sec = Integer.parseInt(split1[2]);

		int now_hour =  Integer.parseInt(split2[0]);
		int now_min = Integer.parseInt(split2[1]);
		int now_sec = Integer.parseInt(split2[2]);

		now_hour = now_hour - lost_hour;
		if(now_hour < 0) {
			// �������� �Ѿ

		}

		now_min = now_min - lost_min;
		if(now_min < 0) {
			now_hour = now_hour -1;
			now_min = now_min + 60;
		}

		now_sec = now_sec - lost_sec;
		if(now_sec < 0) {
			now_min = now_min -1;
			now_sec = now_sec + 60;
		}

		getTime = now_hour * 3600 + now_min * 60 + now_sec;

		return getTime; 
	}

	public void gapValue(ArrayList<Double> list) {
		double tmp = 0.000;
		for(int i = 1; i<list.size(); i++) {
			tmp = Math.abs(list.get(i) - list.get(i-1));	
		}
	}
	
	//�����, �����ִ� �ð��� �� ���
	public void sleep(String date, int value , String time, ArrayList<DataDto> Time1) {
		
		switch(value) {
		
		case 0 : 
			if(sleepFlag == false) {
				nowTime = time;
	//			System.out.println("������� : " + gapTime(LastTime,nowTime));
				DataDto g1 = new DataDto();
				g1.setType(0);
				g1.setValue(gapTime(LastTime, nowTime));
				Time1.add(g1);
				LastTime = time;
			}
		//	System.out.println("�������Դϴ�.");

			sleepFlag = true;

			
	//		System.out.println("Value : " + time);
			break;
			
		case 1 : 
			if(sleepFlag) {
				nowTime = time;
		//		System.out.println("�����ִ� �ð��� : " + gapTime(LastTime,nowTime));
				DataDto g1 = new DataDto();
				g1.setType(1);
				g1.setValue(gapTime(LastTime,nowTime));
				Time1.add(g1);
				LastTime = time;
			}
	//		System.out.println("�����ֽ��ϴ�.");

			sleepFlag = false;
			
		//	System.out.println("Value : " + time);
			break;
			
		case 2 : 
	//		System.out.println("���������ֽ��ϴ�..");
	//		System.out.println("Value : " + time);
			break;

		}
		
	}
	

	public int totalSleep(ArrayList<DataDto> Time1) {
		int totalSleep = 0;
		for(int i = 0; i<Time1.size();i++) {
			if(Time1.get(i).getType() == 0) {
				totalSleep += Time1.get(i).getValue();
			}
			
		}
		
		return totalSleep;
	}

}
