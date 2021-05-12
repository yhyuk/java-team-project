package UserSchedule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import User.User;

/**
 * 
 * 사용자모드에서 회사일정을 조회하는 클래스입니다. 
 * @author User 1조 경지윤
 */
public class UserSchedule {
	
	private static Scanner scan;
	
	/**
	 * 스케줄 클래스의 멤버변수: 글번호(num), 날짜(date), 제목(title), 내용(txt) 
	 */
	private static ArrayList<Schedule> list;
	
	static {
		
		scan = new Scanner(System.in);
		list = new ArrayList<Schedule>();
		
	}

	public UserSchedule(User user) {
		
		/**
		 * 로그인된 사번(매개변수로 받은 user.getId())이 소속된 팀이름으로 -> .txt파일 로드하기
		 */
		String DATA = String.format("data\\캘린더일정_부서마다\\부서별일정_%s.txt", user.getTeam());
		scheduleLoad(DATA); 

		
		Calendar now = Calendar.getInstance();

		/**
		 * 현재 년도와 월로 초기값을 설정
		 */
		int year = now.get(now.YEAR);
		int month = now.get(now.MONTH) + 1;

		boolean loop = true;
		while (loop) {
			
			printCalendar(year, month);

			while (true) {
				System.out.print("번호입력(뒤로가기는 0) : ");
				String sel = scan.nextLine();
				System.out.println();

			
				if (sel.equals("1")) {	//다음페이지
					month--;
					/**
					 * 1월에서 이전페이지를 누르면 작년 12월로 넘어가도록
					 */
					if (month < 1) { 
						year--;
						month = 12;
					}
					printCalendar(year, month);
					

				} else if (sel.equals("2")) {	//이전페이지

					/**
					 * 12월에서 다음페이지를 누르면 내년 1월로 넘어가도록
					 */
					if (month > 11) { 
						year++;
						month = 0;
					}
					printCalendar(year, month + 1);
					month++;

				} else if (sel.equals("3")) { // 자세히보기
					detail(year, month, user);
					break;
				} else if (sel.equals("4")) { // 일정 추가하기

					add(year, month, user);
					save(DATA);
					break;

				}  else if (sel.equals("0")) { // 메인메소드가 있는 클래스로 되돌아가기 
					loop = false;
					break;
				} else {
					System.out.println("잘못 입력했습니다. ");
				}

			}
		}
		
	}
		


	/**
	 * 부서별 일정을 추가한 내용을 출력하는 메소드입니다. 
	 * @param year
	 * @param month
	 * @param user
	 */
	public void add(int year, int month, User user) {
		String addDate = "";
		
		while(true) {
			System.out.printf("추가할 날짜를 입력해주세요(DD) : %d - %02d - ",year,month);
			addDate = scan.nextLine();
	
			if(addDate.length()==1) {
				addDate = "0"+addDate;
				break;
			} else if(addDate.length()>2) {
				System.out.println("올바른 날짜를 입력해주세요.");
			} else {
				break;
			}
		}
		
		while(true) {
			
			System.out.println();
			System.out.println("1. 월간회의\t2.주간회의");
			System.out.println("3. 월간보고\t4.주간보고");
			System.out.println("5. 이벤트");
			System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――");
			System.out.print("추가할 일정의 번호를 입력해주세요. : ");
			String sel = scan.nextLine();
			
			Schedule memo = new Schedule();
			/**
			 * 입력받은 내용을 날짜에 세팅합니다.
			 */
			memo.setNum(list.size()+1);
			memo.setDate(year + "-0" + month + "-" + addDate);
			
			/**
			 * 선택한 일정에따라 일정 제목과 내용을 생성합니다.
			 */
			if(sel.equals("1")) {

				printAdd("월간회의",user, memo);
				break;
				
			} else if(sel.equals("2")) {
				
				printAdd("주간회의",user, memo);
				break;
			
				
			} else if(sel.equals("3")) {
				
				printAdd("월간보고",user, memo);
				break;
				
			} else if(sel.equals("4")) {
				
				printAdd("주간보고",user, memo);
				break;
				
				
			} else if(sel.equals("5")) {
				
				printAdd("이벤트",user, memo);
				break;
				
				
			}else {
				System.out.println("올바른 번호를 입력해주세요.");
			}
		}
		
		
	}



	


	/**
	 * 일정을 추가하는 메소드입니다.
	 * @param 회의제목
	 * @param user 로그인한 직원의 부서
	 * @param memo 
	 */
	public void printAdd(String txt, User user, Schedule memo) {
		
		memo.setTitle(txt);
		if(txt.contains("회의")) {
			memo.setTxt(String.format("%s %s 입니다. 회의 자료 준비하여 참석해주세요.\n",txt, user.getTeam()));
		} else if(txt.contains("보고")) {
			memo.setTxt(String.format("%s %s 입니다. 보고 자료 준비하여 참석해주세요.\n",txt, user.getTeam()));
		} else {
			memo.setTxt(String.format("%s %s 입니다. 편하게 참석해주세요!\n",txt, user.getTeam()));
		}
			
		list.add(memo);
		System.out.println("일정이 추가되었습니다!");
		pause();
	}



	/**
	 * 입력 Block 메소드입니다.
	 */
	public void pause() {
		 System.out.println("엔터를 누르시면 다음으로 진행합니다.");
	      scan.nextLine();//Block		
	}



	/**
	 * 저장하는 메소드입니다. 
	 * @param DATA
	 */
	public void save(String DATA) {

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(DATA));
			String result = "";
			for (Schedule memo : list) {

				// 1000910■2021-07-09■2021-07-10■가족행사■승인
				result += String.format("%s■%s■%s\n%s==========\n"
											,memo.getNum()
											,memo.getDate()
											,memo.getTitle()
											,memo.getTxt());
			}
			writer.write(result);
			writer.close(); // (*************************)

		} catch (Exception e) {
			System.out.println(e);
		}
		
	}



	/**
	 * 캘린더의 부서별 일정을 자세히 보기위한 메소드입니다.<br/>
	 * 해당월의 전체일정과 지정날짜 상세일정으로 확인
	 * @param year : 년도
	 * @param month : 월
	 * @param user : 사용자 정보
	 */

	public void detail(int year, int month, User user) {

		System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――");
		System.out.printf("\t\t1. %s월 전체일정보기\t   2. 날짜별 자세히보기\n", month);
		System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――");
		System.out.print("번호입력(돌아가기는 0):");
		String sel = scan.nextLine();
		while (true) {
			
			
			
			if (sel.equals("1")) {
				/**
				 * 한달 전체일정 <br/>
				 * ( 년도 + 월 ) 까지만 동일하도록
				 */
				String date = year + "-"  + (Integer.toString(month).length() == 1 ? "0" + month : month);
				System.out.println();
				System.out.printf("  ---------------- %s월 전체 일정 ------------ \n", month);
				System.out.println("  [날짜]     \t[제목]  \t[내용]");
	
				for(int i = 0; i < list.size(); i++) {
					/**
					 * 달력의 날짜와 부서일정의 날짜가 같을떄만 출력하기
					 */
					if(list.get(i).getDate().contains(date)) {
						System.out.printf("  (%s)  %s\t%s", list.get(i).getDate(), list.get(i).getTitle(), list.get(i).getTxt());
						//System.out.printf("  일정명 : %s\n", list.get(i).getTitle());
						//System.out.printf("  일정내용 : %s\n", list.get(i).getTxt());
					}
				}
				System.out.println();
				
			} else if (sel.equals("2")) {
				/**
				 * 지정날짜 상세보기
				 */
				System.out.println();
				System.out.print("자세히볼 날짜를 입력해주세요(DD) : ");
				String sel2 = scan.nextLine();
				System.out.println();

				String date = year 
						   + "-"  + (Integer.toString(month).length() == 1 ? "0" + month : month)
						   + "-" + (sel2.length() == 1 ? "0" + sel2 : sel2);
				String text = "";
				for(int i = 0; i < list.size(); i++) {
					if(list.get(i).getDate().contains(date)) {
						text += "  ("+list.get(i).getDate()+") " + list.get(i).getTitle() + " : " + list.get(i).getTxt();
						//text += "  일정명 : "+list.get(i).getTitle()+"\n";
						//text += "  일정내용 : "+list.get(i).getTxt()+"\n";
					}
				}
				
				if(text.equals("")) { 
					System.out.printf("  --------------- %s일 전체 일정 없음 ----------- \n", date);
				}
				
				else {
					System.out.printf("  ---------------- %s일 전체 일정 ------------ \n", date);
					System.out.println(text);
				}
				
				
			} else if (sel.equals("0")) {
				break;
				
			}	else {
				System.out.println("올바른 번호를 입력해주세요.");
			}
			System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――");
			System.out.printf("\t\t1. %s월 전체일정보기\t   2. 날짜별 자세히보기\n", month);
			System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――");
			System.out.print("번호입력(돌아가기는 0):");
			sel = scan.nextLine();
		}
	}
	



	/**
	 * 달력을 출력하는 메소드입니다.
	 * @param year : 현재 시간의 년도
	 * @param month : 현재 시간의 월
	 */
	public static void printCalendar(int year, int month) {

		System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――");
		System.out.println("                    [회사 일정보기]");
		System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――");
		
		System.out.println();
		System.out.println(year + "년\t\t\t\t\t\t" + getMonthName(month));
		System.out.println("===================================================");
		System.out.println("일\t월\t화\t수\t목\t금\t토");
		System.out.println("===================================================");

		int count = 0;

		boolean tabFlag = false;
		
		String dateNo = "";
		String schedule = "";
		
		for (int i = 1; i <= getNumberOfDaysInMonth(year, month); i++) {
			
			/**
			 * 1일을 첫시작 요일까지 탭해주기위해
			 */
			if (i < 2) {
				for (int y = 1; y <= getStartDay(year, month); y++) {
					System.out.print("\t");
					//일정도 같이 프린트 되어야하기때문에 첫요일까지 같이 탭해주기 
					schedule += "\t";
					/**
					 * 7번째 칸에 도달하면 줄바꿈할수있도록 탭할때마다 count가감하기<br/>
					 * 한주(1개의 line) = 7일 
					 */
					count += 1;
				}
			}
			
			if (tabFlag) {
				
				dateNo += "\t";
			}
				//System.out.printf("\t", i);
			
			dateNo += Integer.toString(i);
			//System.out.printf("%2d", i);
			
			
			/**
			 * 부서별 일정 캘린더에 넣기
			 */
			int meetingCnt = 0;
			int reportCnt = 0;
			int eventCnt = 0;
			
			for(int j = 0; j < list.size(); j++) {
				
			/**
			 * 달력을 부서별일정.txt에 있는 날짜와 똑같은 형식으로 만들어주기 
			 */
				String tmpDate = year + "-" 
			                   + (Integer.toString(month).length() == 1 ? "0" + month : month) + "-"
			                   + (Integer.toString(i).length() == 1 ? "0" + i : i); 
				
				/**
				 * 일정 파일의 날짜와 달력의 날짜비교<br/>
				 * 일정이있는 달력의 날짜에 개수 기록하기 
				 */
				if(list.get(j).getDate().equals(tmpDate)) {
					if(list.get(j).getTitle().contains("회의")) {
						meetingCnt++;
						//schedule += "  ○\t";
					}else if(list.get(j).getTitle().contains("보고")) {
						reportCnt++;
						//schedule += "  ◇\t";
					}else if(list.get(j).getTitle().equals("이벤트")) {
						eventCnt++;
						//schedule += "  ◇\t";
					}
				}
			}
			
			/**
			 * 아무 일정 없을때 해당 일 공백으로 띄우기 
			 */
			if(meetingCnt == 0 && reportCnt == 0 && eventCnt == 0) {
				schedule += "\t";
			} else {
				
				//프린트
				
				//스케줄이 2개일때 공백이 아니면 \t을 하도록
				schedule +=  schedule.trim().equals("") ? "" : "\t";
				schedule +=  meetingCnt > 0 ? "○"+meetingCnt : "";
				schedule +=  reportCnt > 0 ? "◇"+reportCnt : "";
				schedule +=  eventCnt > 0 ? "☆"+eventCnt : "";
			}
			
			
			/**
			 * 날짜와 일정 모두 출력하는 부분입니다. 
			 */
			count += 1;
			if (count == 7) {
				tabFlag = false;
				System.out.println(dateNo);
				System.out.println(schedule);
				System.out.println();
				count = 0;
				dateNo = "";
				schedule = "";
			} else
				tabFlag = true;
			
		
		} //전체 for문

		System.out.println("회의 : ○ / 보고: ◇ / 이벤트 : ☆");
		System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――");
		System.out.println("\t\t 1.이전페이지    2.다음페이지    3.상세일정");
		System.out.println("\t\t\t\t       4.일정 추가하기");
		System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――");
		
	}

	/**
	 * 매월 1일이 무슨 요일에 시작하는지 알아내는 메소드입니다.
	 * @param year
	 * @param month
	 * @return 해당 달의 첫 요일
	 */
	public static int getStartDay(int year, int month) {

		
		
		  int monthSum = 0;
	        int leapYear = 0;
	        int daySum = 1;
	 
	        for (int i = 1; i < year; i++) {
	            monthSum += 365;
	            if (isLeapYear(i) == true) {
	                leapYear += 1;
	            }
	        }
	 
	        for (int j = 1; j < month; j++) {
	            daySum += getNumberOfDaysInMonth(year, j);
	        }
	 
	        return (monthSum + leapYear + daySum) % 7;
	 
	}
	
	/**
	 * 해당년도가 윤년인지 평년인지 알아내는 메소드입니다.
	 * @param i : 
	 * @return 윤년 or 평년
	 */
	public static boolean isLeapYear(int year) {
		
		return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
		
	}

	/**
	 * 해당 월이 30,31,29,28중 무엇인지 알아내는 메소드입니다.
	 * @param year
	 * @param month
	 * @return 달의 마지막 일
	 */
	public static int getNumberOfDaysInMonth(int year, int month) {
		

        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else if (month == 2 && isLeapYear(year) == true) {
            return 29;
        } else if (month == 2 && isLeapYear(year) == false) {
            return 28;
        } else {
            return 31;
        }
		
	}

	/**
	 * 캘린더 헤드부분의 해당월을 출력시키는 메소드입니다. 
	 * @param month
	 * @return 해당 월을 출력합니다. [Ex] 5월
	 */
	public static String getMonthName(int month) {

	    String monthName = null;
        switch (month) {
        case 1:
            monthName = "1월";
            break;
        case 2:
            monthName = "2월";
            break;
        case 3:
            monthName = "3월";
            break;
        case 4:
            monthName = "4월";
            break;
        case 5:
            monthName = "5월";
            break;
        case 6:
            monthName = "6월";
            break;
        case 7:
            monthName = "7월";
            break;
        case 8:
            monthName = "8월";
            break;
        case 9:
            monthName = "9월";
            break;
        case 10:
            monthName = "10월";
            break;
        case 11:
            monthName = "11월";
            break;
        case 12:
            monthName = "12월";
            break;
        }
        return monthName;
	
	}

	/**
	 * 부서별일정_.txt파일을 로드하는 메소드입니다.
	 * @param DATA 부서별 일정 
	 */
	public void scheduleLoad(String DATA) {
		
		try {
			list.clear();
			BufferedReader reader = new BufferedReader(new FileReader(DATA));
			String line = "";
			
			
			while ((line = reader.readLine()) != null) {

			
				
				//헤드부분 
				Schedule memo = new Schedule();

				String[] temp = line.split("■");
				memo.setNum(Integer.parseInt(temp[0]));
				memo.setDate(temp[1]);
				memo.setTitle(temp[2]);

				//line = reader.readLine();
				String text = "";
				while(!(line = reader.readLine()).equals("==========")) {
					text  += line +"\r\n";
				}
				memo.setTxt(text);
				
				list.add(memo);

			} // while

			reader.close();


		} catch (Exception e) {
			
			System.out.println("load: " + e);
			
		}
		
	}
}
