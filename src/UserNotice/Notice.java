package UserNotice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 사용자와 관리자의 공지사항 조상 클래스
 * @author 82106
 *
 */
public class Notice {
	
    protected static Scanner scan;
    protected final static String DATA;
    protected static ArrayList<NoticeMemo> list;
    protected final static int N = 10;

    /**
     * static 생성자
     */
    static {
        scan = new Scanner(System.in);
        DATA = "data\\공지사항.txt";
        list = new ArrayList<NoticeMemo>();
    }

    /**
     * 프로그램 진행을 잠시 멈추는 메소드
     */
    public static void stop() {
		System.out.println("\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
        System.out.println("\n계속하시려면 엔터를 입력해주세요.");
        scan.nextLine();
    }

    /**
     * 번호를 선택받아 공지사항을 자세히 출력하는 메소드
     */
    public static void noticeDetail() {
        System.out.print("자세히 볼 번호 입력: ");
        int sel = scan.nextInt();

        for(NoticeMemo memo : list) {
            if(memo.getNum() == sel) {
                System.out.printf("\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n\n\t\t\t\t[자세히 보기]\n\n[번호] %d\t\t[제목] %s\t\t[날짜] %s\n\n\n%20s"
                        , memo.getNum()
                        , memo.getTitle()
                        , memo.getDate()
                        , memo.getContent());
                break;
            }
        }
    }

    /**
     * 공지사항을 데이터파일에서 읽어와서 객체로 만든 후 list에 저장하는 메소드
     */
    public static void load() {
    	list.clear();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(DATA));

            String line = "";

            while((line = reader.readLine()) != null) {

                NoticeMemo noticeMemo = new NoticeMemo();

                String[] temp = line.split("■"); //■단위로 잘라서 배열에 저장

                //배열을 객체에 저장
                noticeMemo.setNum(Integer.parseInt(temp[0])); //번호
                noticeMemo.setTitle(temp[1]); //제목
                noticeMemo.setDate(temp[2]); //날짜
                noticeMemo.setReadTargetTeam(temp[3]); //배포대상-팀
                noticeMemo.setReadTargetPosition(temp[4]); //배포대상-직급

                String tempContent = "";

                while(!(line = reader.readLine()).equals("==========")) {
                    tempContent += line + "\r\n";
                }

                noticeMemo.setContent(tempContent); //내용

                list.add(noticeMemo);//ArrayList에 객체저장
            }

            reader.close();

        } catch (Exception e) {

            System.out.println(e);
        }
    }
}
