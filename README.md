> <h1>과제 제목  - WhereIsMyHome</h1>

> <h2>Description</h2> 
이사를 하고자 할 동네에서 원하는 아파트, 주택을 찾아낼 목적으로 매매/전,월세 거래 정보를 기본 서비스로 구현하고, 주변 관광데이터 정보등을 추가 서비스로 구현했다.
> <h2>Technology</h2>
  * 기본 기능
    * Lambda 식으로 표현
    * search() 를 Dao Layer 의 method 를 호출
    * 화면 목록에서 선택된 항목 번호로 상세 정보를 얻어 HomeDeal 객체로 return 
    * APTRentSAXHandler 의 부분코드를 완성
    * HomeDealSAXHandler 의 전체코드를 완성
  * 추가 기능
    * 상세정보에 해당 사진 변화
    * 아이콘
    * 관광데이터 정보를 통해 같은 동에 해당하는 분류,명칭,동을 보여준다.
  
> <h2>기본 기능</h2>
<br>
<b>기능 설명 </b>
<p>아파트/ 주택 별, 매매 및 전, 월세 거래 내역 정보와 아파트 정보 파일의 XML을 읽어 고객이 원하는 주택 정보를 검색하고 그 결과를 화면에 표시했다. 검색은 동 별, 아파트 이름 별, 아파트 매매/ 전,월세/ 주택 매매/ 주택 전,월세 등을 통해 분류했다.</p>
<b>구현 방법 </b>
<p>주택 거래 정보가 있는 XML 문서를 parsing하여 객체화 했고, 원하는 정보를 가져와 결과로 보여줬다.</p>


| 번호    | Package & Class                                 | 구현 내용                                                                 | 비고     |
| ------- | ----------------------------------------------- | ------------------------------------------------------------------------- | -------- |
| 01      | com.ssafy.rent.view. - HomeInfoView             | searchBt (검색버튼 객체의 Click Event Handler 등록을 Lambda 식으로 표현)  | 234 line |
| 02      | com.ssafy.rent.model.service. - HomeServiceImpl | search() 를 Dao Layer 의 method 를 호출하도록 완성.                       | 34 line  |
| 03      | com.ssafy.rent.model.dao. - HomeDaoImpl         | 화면 목록에서 선택된 항목 번호로 상세 정보를 얻어 HomeDeal 객체로 return. | 80 line  |
| 04 ~ 07 | com.ssafy.rent.util. - APTRentSAXHandler        | APTRentSAXHandler 의 부분코드를 완성.                                     | 28 line  |
| 08      | com.ssafy.rent.util. - HomeDealSAXHandler       | HomeDealSAXHandler 의 전체코드를 완성                                     | ALL      |

<01>
```
searchBt.addActionListener(e ->{
			searchHomes();
		});   
```
<02>
```
@Override
	public HomeDeal search(int no) {
		return dao.search(no);	
	}
}
```
<03>
```
public HomeDeal search(int no) {
		
		String csvFile = "./res/travelData.csv";

        ArrayList<TourismInfo> tour = new ArrayList<TourismInfo>();
        ArrayList<String> RandomTour = new ArrayList<String>();
        tour = (ArrayList<TourismInfo>) new TravelSaxParser().read(csvFile);
        tourlist = tour;     
		for(int i=0;i<search.size();i++) {
			
            if (((search.get(i)).getNo()) == no) {         	
            	for(int j=0;j<tourlist.size();j++) {
            		if (tourlist.get(j).getDong().contains((search.get(i)).getDong())) {	
            			RandomTour.add(tourlist.get(j).getTourname().replaceAll("\"",""));
            		}
            	}
            	if(RandomTour.size()>=3)
            	{
            	System.out.println("**********관광지 목록 Top3***********");
            	for(int k=0;k<3;k++) {
            		
            		System.out.println(RandomTour.get(k));
            	}
            	}
                return search.get(i);
            }
        }
        return null;
	}
```    
<04 - 07>

```
public class APTRentSAXHandler extends DefaultHandler {
	/**아파트 거래 정보를 담는다 */
	private List<HomeDeal> homes;
	/**파상힌 아파트 거래 정보*/
	private HomeDeal home;
	
	
	/**태그 바디 정보를 임시로 저장*/
	private String temp;
	public APTRentSAXHandler(){
		homes = new LinkedList<HomeDeal>();
	}
	public void startElement(String uri, String localName
			                           , String qName, Attributes att ){
		if(qName.equals("item")){
			// complete code #04
			// APTDealSAXHandler Class 를 참조하여, 아파트 전월세 거래 정보에 맞도록 코드를 추가하세요.
			home = new HomeDeal(HomeSaxParser.no++);
			home.setType(HomeDeal.APT_RENT);
			homes.add(home);
		}
	}
	public void endElement(String uri, String localName, String qName){
		if(qName.equals("지역코드")) { 
			home.setCode(Integer.parseInt(temp));
		}else if(qName.equals("아파트")) {
			File file = new File("./img/");
			// System.out.println(file.getAbsolutePath()+" "+file.exists());
			String homeName = temp.trim();
			home.setAptName(homeName);
			//home.setImg(homeName);
		}else if(qName.equals("법정동")) { 
			home.setDong(temp.trim());
		}else if(qName.equals("보증금액")) { 
				home.setDealAmount(temp);
			// complete code #05
			// 보증금액 항목을 처리하세요.
		}else if(qName.equals("월세금액")) { 
			home.setRentMoney(temp);
		}else if(qName.equals("건축년도")) { 
			home.setBuildYear(Integer.parseInt(temp));
			// complete code #06
			// 건축년도 항목을 처리하세요.
		}else if(qName.equals("년")) { 
			home.setDealYear(Integer.parseInt(temp));
		}else if(qName.equals("월")) { 
			home.setDealMonth(Integer.parseInt(temp));
		}else if(qName.equals("일")) { 
			home.setDealDay(Integer.parseInt(temp));
			// complete code #07;
			// 일 항목을 처리하세요.
		}else if(qName.equals("전용면적")) { 
			home.setArea(Double.parseDouble(temp));
		}else if(qName.equals("지번")) { 
			home.setJibun(temp);
		}else if(qName.equals("층")) { 
			home.setFloor(Integer.parseInt(temp));
		}
	}
	public void characters(char[]ch, int start, int length){
		temp = new String(ch, start, length);
	}
	public List<HomeDeal> getHomes() {
		return homes;
	}
	public void setHomes(List<HomeDeal> homes) {
		this.homes = homes;
	}
}
```
<08>
```
public class HomeDealSAXHandler extends DefaultHandler {
	// complete code #08
	// APTDealSAXHandler Class 를 참조하여, 주택 거래 정보에 맞도록 전체 코드를 작성하세요. 단, 아래 코드를 이용하여 완성하세요.
	/**아파트 거래 정보를 담는다 */
	private List<HomeDeal> homes;
	/**파상힌 아파트 거래 정보*/
	private HomeDeal home;
	/**태그 바디 정보를 임시로 저장*/
	private String temp;
	 public HomeDealSAXHandler(){
	        homes = new LinkedList<HomeDeal>();
	    }
	public void startElement(String uri, String localName, String qName, Attributes att ){
		if(qName.equals("item")){
			
			home = new HomeDeal(HomeSaxParser.no++);
			home.setType(HomeDeal.HOME_DEAL);
			homes.add(home);
	}}
	public void endElement(String uri, String localName, String qName){
		if(qName.equals("지역코드")) { 
			home.setCode(Integer.parseInt(temp));
		}else if(qName.equals("연립다세대")) { 
			home.setAptName(temp.trim());
		}else if(qName.equals("법정동")) { 
			home.setDong(temp.trim());
		
		}else if(qName.equals("거래금액")) { 
			home.setDealAmount(temp);
		}else if(qName.equals("건축년도")) { 
			home.setBuildYear(Integer.parseInt(temp));
			
		}else if(qName.equals("년")) { 
			home.setDealYear(Integer.parseInt(temp));
		}else if(qName.equals("월")) { 
			home.setDealMonth(Integer.parseInt(temp));
		}else if(qName.equals("일")) { 
			home.setDealDay(Integer.parseInt(temp));
		}else if(qName.equals("대지권면적")) { 
			home.setArea(Double.parseDouble(temp));
		
		}else if(qName.equals("전용면적")){home.setArea(Double.parseDouble(temp));}
		else if(qName.equals("지번")) { 
			home.setJibun(temp);
		}else if(qName.equals("층")) { 
			home.setFloor(Integer.parseInt(temp));
		}
	}
	public void characters(char[]ch, int start, int length){temp = new String(ch, start, length);}
	public List<HomeDeal> getHomes() { return homes; }
	public void setHomes(List<HomeDeal> homes) {this.homes = homes;}
}
```

<b>결과 </b>
<p>1. 아파트/주택 매매/전,월세 거래 정보에 대해 법정동 또는 아파트 이름으로 검색이 가능하다. </p>
<p>2. 검색된 아파트/주택에 대해 상세 정보를 확인할 수 있다.</p>

<br>

* 기본 화면 구성
<br>
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FboUrdb%2FbtrIwG6nsWF%2FoCC3QFBrJIG9wBmZoImux0%2Fimg.png" width="100%"  height="30%" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>





<br>
<br>
<br>



> <h2>추가 기능</h2>
<br>
<h2>상세정보에 해당 사진 변화</h2>
<b>기능 설명 </b>
<p>원하는 정보 (아파트 이름, 번호, 동 등등)을 클릭하면 상세정보의 사진이 해당 사진으로 변경되도록 만들었다.  </p>
<b>구현 방법 </b>

| 번호 | Package & Class                           | 구현 내용                                                                                                                    | 비고    |
| ---- | ----------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- | ------- |
| 01   | APTInfo.xml                               | AptInfo에 img태그에 아파트 그림에 대한 정보를 확인                                                                           | 전체    |
| 02   | com.ssafy.rent.util. - HomeSaxParser.java | aptDeals를 탐색하면서 같은 아파트 이름으로 되어있는 경우 setImg를 사용해서 이미지정보를 넣어준다.                            | 53 line |
| 03   | com.ssafy.rent.view. - HomeInfoView.java  | 만약 이미지가 없으면 그냥 기본이미지로 생성하고 이미지가 있으면 ImageIcon을 통해 "img/" 경로에 저장한 이미지 가져와 붙어주기 | 105-    |

<02>
<pre>
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FmyKDi%2FbtrIwHRE1SY%2FFYAydsZLKix3d9e0YuKQYK%2Fimg.png" width="100%"  height="30%" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>
</pre>
<03>
<pre>
ImageIcon icon = null;
if( curHome.getImg() != null && curHome.getImg().trim().length() != 0) {
    icon = new ImageIcon("img/" + curHome.getImg());
    System.out.println("#####" + icon.toString() + "####");
}else {
    icon = new ImageIcon("img/다세대주택.jpg");
}

imgL.setIcon(icon);
    
    

Image img = null;
try {
    img = ImageIO.read(new File("img/"+curHome.getImg()));
    } catch (IOException ex) {
        try {
            img = ImageIO.read(new File("img/다세대주택.jpg"));
    } catch (Exception e) {
    }
    }
img = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
imgL.setIcon(new ImageIcon(img));
</pre>
<b>결과 </b>

<br>

* 사진 변경 구성
<br>
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FlrU0e%2FbtrIuY0I2p4%2FHvBRnE6NSPo8mdX7jq53I1%2Fimg.png" width="100%" height="30%" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>





<br>
<br>
<br>

*****
<h2>아이콘</h2>
<b>기능 설명 </b>
<p>아이콘의 이미지를 특성에 맞게 바꿈</p>
<b>구현 방법 </b>
<br>

| 번호 | Package & Class                     | 구현 내용                                                                      | 비고     |
| ---- | ----------------------------------- | ------------------------------------------------------------------------------ | -------- |
| 01   | com.ssafy.rent.view -  HomeInfoView | ImageIcon을 이용해서 이미지 가져오고 frame.setIconImage을 통해 화면에 보여주기 | 137 line |

<01>
<pre>
ImageIcon img1 = new ImageIcon("./house.PNG");
frame.setIconImage(img1.getImage());
</pre>

<br>

* 아이콘 구성
<br>
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcpC6bl%2FbtrIuYGpAmr%2F8HkTI34hqxXAxHefgjUhC0%2Fimg.png" width="100%" height="30%" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>





<br>
<br>

*****

<h2>관광데이터 정보를 통해 같은 동에 해당하는 분류,명칭,동을 보여줌</h2>
<br>
<b>기능 설명 </b>
<p>종로구에 있는 관광데이터 정보를 통해 아파트정보에 있는 동과 같은 동에 해당하는 분류, 명칭, 동을 comsole에 보여주도록 했다. 원하는 정보를 클릭하면 주변에 살펴 볼 수 있는 관광명소를 보여준다. 사용 예시로는 평창동이 있다.</p>

<b>구현 방법 </b>
| 번호 | Package & Class                              | 구현 내용                                                                                               | 비고 |
| ---- | -------------------------------------------- | ------------------------------------------------------------------------------------------------------- | ---- |
| 01   | com.ssafy.rent.model.dto. - TourismInfo.java | TourismInfo에 해당하는 정보의 get,set,toString을 생성해줍니다. (dong, Tourname, classification)         | 전체 |
| 02   | com.ssafy.rent.util. - TravelSaxParser.java  | 서울시 종로구 관광데이터 정보 (한국어)의 csv를 읽고, ","를 기준으로 분류, 명칭, 행정동을 parsing 합니다 | ---  |
| 03   | com.ssafy.rent.model.dao. - HomeDaoImpl      | 찾는 정보의 동이 tourlist의 동에 포함되어 있으면 출력해주기                                             | 85-  |

<참고 자료> [관광데이터](https://data.seoul.go.kr/dataList/OA-12957/S/1/datasetView.do)

<01>
```
public class TourismInfo {
    private String dong;
    private String Tourname;
    private String classification;
    
    public String getDong() {
        return dong;
    }
    public void setDong(String dong) {
        this.dong = dong;
    }
    public String getTourname() {
        return Tourname;
    }
    public void setTourname(String tourname) {
        this.Tourname = tourname;
    }
    public String getClassification() {
        return classification;
    }
    public void setClassification(String classification) {
        this.classification = classification;
    }
    @Override
    public String toString() {
        return "TourismInfo [dong=" + dong + ", Tourname=" + Tourname + ", classification=" + classification + "]";
    }
    
}
```



<02>
```
public class TravelSaxParser {
    // public List<TourismInfo> tourList= new ArrayList<TourismInfo>();
    //public Map<String, TourismInfo> tourList;
    //private TourismInfo tourisminfo;
    private int size;

    public List<TourismInfo> read(String csvFile) {
        List<TourismInfo> tourList = new ArrayList<>();
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);

            BufferedReader br = new BufferedReader(fr);
            String temp = br.readLine();
            
            while ((temp = br.readLine()) != null) {
//              }
                TourismInfo info = new TourismInfo();
                StringTokenizer st = new StringTokenizer(temp, ",");
                for (int i = 0; i < 12; i++) {
                    String tempToken = st.nextToken();
                    if (i == 1) {
                        info.setClassification(tempToken);
                        //System.out.print(tempToken+"/");
                    } else if (i == 2) {
                        info.setTourname(tempToken);
                        //System.out.print(tempToken+"/");
                    } else if (i == 6) {
                        info.setDong(tempToken);
                        //System.out.println(tempToken);
                        tourList.add(info);
                    }
                    
                }
            }
            // temp= br.readLine();}
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return tourList;
    }

    public static void main(String[] args) {
        String csvFile = "./res/travelData.csv";
        new TravelSaxParser().read(csvFile);

    }

}
```

<03>
```
    /**
     * 아파트 식별 번호에 해당하는 아파트 거래 정보를 검색해서 반환한다.<br/>
     * 법정동+아파트명을 이용하여 HomeInfo에서 건축연도, 법정코드, 지번, 이미지 정보를 찾아서 HomeDeal에 setting한 정보를 반환한다. 
     * @param no    검색할 아파트 식별 번호
     * @return        아파트 식별 번호에 해당하는 아파트 거래 정보를 찾아서 리턴한다, 없으면 null이 리턴됨
     */
    public HomeDeal search(int no) {
        
        String csvFile = "./res/travelData.csv";

        ArrayList<TourismInfo> tour = new ArrayList<TourismInfo>();
        ArrayList<String> RandomTour = new ArrayList<String>();
        tour = (ArrayList<TourismInfo>) new TravelSaxParser().read(csvFile);
        tourlist = tour;     
        for(int i=0;i<search.size();i++) {
            
            if (((search.get(i)).getNo()) == no) {             
                for(int j=0;j<tourlist.size();j++) {
                    if (tourlist.get(j).getDong().contains((search.get(i)).getDong())) {    
                        RandomTour.add(tourlist.get(j).getTourname().replaceAll("\"",""));
                    }
                }
                if(RandomTour.size()>=3)
                {
                System.out.println("**********관광지 목록 Top3***********");
                for(int k=0;k<3;k++) {
                    
                    System.out.println(RandomTour.get(k));
                }
                }
                return search.get(i);
            }
        }
        return null;
    }
    
```

<b> 결과 </b>


<br>

* 관광데이터 구성
<br>
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdLSsNT%2FbtrIyYrmON5%2FL3UIfkAvZheOLLhlB4bDT1%2Fimg.png" width="100%" height="30%" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>





<br>
<br>
