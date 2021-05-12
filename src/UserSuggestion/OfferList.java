package UserSuggestion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * 관리자, 사용자 건의사항 조상 클래스
 * @author 82106
 *
 */
public class OfferList {
	
	protected static Scanner scan;
	protected final static String DATA;
	protected static ArrayList<OfferMemo> list;
	protected final static int N = 10; // number of memo list -> 목록을 10개씩 보여주는 상수
	private static ArrayList<Integer> numArr; // 데이터 번호 관리
	
	static {
		scan = new Scanner(System.in);
		DATA = "data\\건의사항.txt";
		list = new ArrayList<OfferMemo>();
		numArr = new ArrayList<Integer>();
	}
	
	
	/**
	 * 사용자가 건의했었던 내역을 목록으로 보여주는 메소드
	 * @param start 건의사항 시작 페이지
	 * @param end 건의사항 마지막 페이지
	 */
	public static void printOffer(int start, int end) {
		
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("\n                                                [ 건의사항 목록 ]");
		System.out.println("\n[번호]\t    [사번]\t\t [제목]\t\t\t\t[날짜]\t\t\t\t[내용]\n");
		
		for(int i=start; i<end && i < list.size(); i++) {
			OfferMemo memo = list.get(i);
			String content = memo.getContent().replace("\r\n", " ");
			String title = memo.getTitle();

			if (content.length() > 14) {
				content = content.substring(0, 14) + "...";
				title = title.substring(0, 9) + "...";
			}


			
			System.out.printf("\n%3d%15s%24s%24s%30s\n"
				, memo.getNum()
				, memo.getId()
				, title
				, memo.getDate()
				, content);
		}
		
	}
	
	/**
	 * list에 저장된 내용을 데이터파일에 저장하는 메소드
	 */
	public static void save() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(DATA));
			for(OfferMemo memo : list) {
				writer.write(String.format("%d■%s■%s■%s■\n%s==========\n"
						, memo.getNum()
						, memo.getId()
						, memo.getTitle()
						, memo.getDate()
						, memo.getContent()));
			}
			
			writer.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	/**
	 * 데이터 파일에서 ■단위로 나눈 후 객체로 만들어 list에 저장하는 메소드
	 */
	public static void load() {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(DATA));
			
			String line = "";
			
			while ((line = reader.readLine()) != null) {
				OfferMemo offerMemo = new OfferMemo(); 
				
				String[] temp = line.split("■");
				int num = Integer.parseInt(temp[0]);
				
				offerMemo.setNum(num); //번호
				offerMemo.setId(temp[1]); //사번
				offerMemo.setTitle(temp[2]); //제목
				offerMemo.setDate(temp[3]); //날짜

				numArr.add(num);
				
				String content = "";
				
				while (!(line = reader.readLine()).equals("==========")) {
					content += line + "\r\n";
				}
				
				offerMemo.setContent(content);
				
				list.add(offerMemo);
			}

			
			reader.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	/**
	 * 건의사항 목록에서 자세히 볼 번호를 입력받아 내용을 보여주는 메소드
	 */
	public static void offerReadDetail() {

		System.out.print("자세히 볼 번호 선택: ");
		int num = scan.nextInt();

		for (OfferMemo memo : list) {
			if (memo.getNum() == num) {

				System.out.printf(
						"\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n\n\t\t\t\t\t\t[ 자세히 보기 ]\n\n[번호]\t%d\t[사번] %s\t\t[제목] %s\t\t[날짜] %s\n\n%20s"
						, memo.getNum()
						, memo.getId()
						, memo.getTitle()
						, memo.getDate()
						, memo.getContent());

			}

		}

	}

	/**
	 * 다음 행동으로 넘어가기 전 일시정지 역할을 하는 메소드
	 */
	public static void stop() {
		System.out.println("\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.print("\n계속 하시려면 엔터를 입력해주세요.");
		scan.nextLine();
	}
	
	/**
	 * 건의사항 입력시 번호를 순차적으로 저장할 수 있게 관리하는 메소드
	 * @return 마지막에 저장된 번호 + 1
	 */
	public static int num() {
		int max = 0;

		for(Integer number : numArr) {
			if(number > max){
				max = number;
			}
		}
		return max + 1; 
	}
}