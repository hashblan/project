//05.21 완료
package gui_basic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class StudentGUIUI extends JFrame implements ActionListener, FocusListener{
	private JLabel id_L, name_L, score_L;
	private JTextField id_T, name_T, score_T;
	private JTextArea output_T, check_T;
	private JButton regist_B, view_B, exit_B, modify_B, delete_B;
	private JButton name_asc_B, score_asc_B, name_desc_B, score_desc_B, id_asc_B, id_desc_B;
	private JPanel side_P, input_P, south_P, center_P;
	private StudentDAO dao;
	private JMenuBar mb = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem openItem = new JMenuItem("OPEN");
	private JMenuItem saveItem = new JMenuItem("SAVE");
	private JFileChooser fc = new JFileChooser();
	private JTextArea ta = new JTextArea();
//  삭제 다이얼로그
	private JTextField tf = new JTextField(10);
//	등록, 조회
	StudentGUIUI() {
		this.setTitle("학생관리프로그램");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.formDesign();
		this.eventHandler();
		this.setSize(900,300);
		this.setVisible(true);
		dao = new StudentDAO();
	}
	public void formDesign() {	
		center_P = new JPanel(new GridLayout(2,1));
		side_P = new JPanel();
		input_P = new JPanel();
		south_P = new JPanel(new GridLayout(2,1));
		id_L = new JLabel("ID : ");
		name_L = new JLabel("이름 : ");
		score_L = new JLabel("성적 : ");
		id_T = new JTextField();
		name_T = new JTextField();
		score_T = new JTextField();
		output_T = new JTextArea();
		check_T = new JTextArea();
		center_P.add(output_T);
		center_P.add(check_T);
		regist_B = new JButton("등록");
		view_B = new JButton("조회");
		modify_B = new JButton("수정");
		delete_B = new JButton("삭제");
		name_asc_B = new JButton("이름오름차순정렬");
		name_desc_B = new JButton("이름내림차순정렬");
		score_asc_B = new JButton("성적오름차순정렬");
		score_desc_B = new JButton("성적내림차순정렬");
		id_asc_B = new JButton("ID오름차순정렬");
		id_desc_B = new JButton("ID내림차순정렬");
		exit_B = new JButton("종료");
//		West 영역에 컴포넌트 배치
		this.add(side_P, BorderLayout.WEST);
		side_P.setLayout(new GridLayout(2,1));
		side_P.add(input_P);
		input_P.setLayout(new GridLayout(3,2));
		input_P.add(id_L);
		input_P.add(id_T);
		input_P.add(name_L);
		input_P.add(name_T);
		input_P.add(score_L);
		input_P.add(score_T);
//		South영역에 배치
		this.add(south_P, BorderLayout.SOUTH);
		south_P.add(regist_B);
		south_P.add(view_B);
		south_P.add(modify_B);
		south_P.add(delete_B);
		south_P.add(name_asc_B);
		south_P.add(name_desc_B);
		south_P.add(score_asc_B);
		south_P.add(score_desc_B);
		south_P.add(id_asc_B);
		south_P.add(id_desc_B);
		south_P.add(exit_B);
//		Center영역에 배치
		center_P.add(output_T);
		center_P.add(check_T);
		check_T.setBackground(Color.green);
		this.add(center_P, BorderLayout.CENTER);
//		이벤트가 처리되기전에 
		name_T.setEditable(false);
		score_T.setEditable(false);
//		종료버튼만 활성화
		regist_B.setEnabled(false);
		view_B.setEnabled(false);
		modify_B.setEnabled(false);
		delete_B.setEnabled(false);
		name_asc_B.setEnabled(false);
		name_desc_B.setEnabled(false);
		score_asc_B.setEnabled(false);
		score_desc_B.setEnabled(false);
		id_asc_B.setEnabled(false);
		id_desc_B.setEnabled(false);
//		파일메뉴
		mb.add(fileMenu);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		this.setJMenuBar(mb);
	}
	public void eventHandler() {
//		등록버튼출력
		regist_B.addActionListener(this);
		view_B.addActionListener(this);
		exit_B.addActionListener(this);
		id_T.addFocusListener(this);
		name_T.addFocusListener(this);
		score_T.addFocusListener(this);
//		열기, 저장하기 수행
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		delete_B.addActionListener(this);
		modify_B.addActionListener(this);
		name_asc_B.addActionListener(this);
		name_desc_B.addActionListener(this);
		score_asc_B.addActionListener(this);
		score_desc_B.addActionListener(this);
		id_asc_B.addActionListener(this);
		id_desc_B.addActionListener(this);
	}
	public static void main(String[] args) {
		new StudentGUIUI();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("등록")) {
		    String id = id_T.getText();
		    String name = name_T.getText();
		    String scoreStr = score_T.getText();
		    if (scoreStr.matches("\\d+")) {
		        int score = Integer.parseInt(scoreStr);
		        if (dao.create(id, name, score)) {
		            output_T.setText("등록이 되었습니다.");
		        } else {
		            if (!name.matches("[a-zA-Z가-힣]+")) {
		                JOptionPane.showMessageDialog(null, "올바른 이름 형식이 아닙니다.", "이름 등록 실패", JOptionPane.ERROR_MESSAGE);
		            } else if (id.matches("[a-zA-Z가-힣]+")) {
		                JOptionPane.showMessageDialog(null, "올바른 ID 형식이 아닙니다. 숫자만 입력하세요.", "ID 등록 실패", JOptionPane.ERROR_MESSAGE);
		            } else if (id.length() != 7) {
		                JOptionPane.showMessageDialog(null, "ID 7자리를 입력해주세요", "ID 등록 실패", JOptionPane.ERROR_MESSAGE);
		            } else if (score < 0 || score > 100) {
		                JOptionPane.showMessageDialog(null, "성적은 0~100 사이 숫자만 입력 가능합니다.", "성적 등록 실패", JOptionPane.ERROR_MESSAGE);
		            } else if (dao.checkIdExists(id)) {
		                JOptionPane.showMessageDialog(null, "중복된 ID입니다.", "ID 등록 실패", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		    } else {
		        JOptionPane.showMessageDialog(null, "성적은 숫자만 입력 가능합니다.", "성적 등록 실패", JOptionPane.ERROR_MESSAGE);
		    }
			id_T.setText("");
			name_T.setText("");
			score_T.setText("");
			view_B.setEnabled(true);
			regist_B.setEnabled(false);
			modify_B.setEnabled(true);
			delete_B.setEnabled(true);
			
			if(dao.check()) {
				score_asc_B.setEnabled(true);
				score_desc_B.setEnabled(true);
				name_asc_B.setEnabled(true);
				name_desc_B.setEnabled(true);
				id_asc_B.setEnabled(true);
				id_desc_B.setEnabled(true);
			}	
		}
//		if(e.getActionCommand().equals("등록")) {
//			if(dao.create(id_T.getText(),name_T.getText(), Integer.parseInt(score_T.getText()))) {
//				output_T.setText("등록이 되었습니다.");
//			}
//			else{
//				if(!name_T.getText().matches("[a-zA-z가-힣]+")) {
//					JOptionPane.showMessageDialog(null, "올바른 이름 형식이 아닙니다.", "이름 등록 실패", JOptionPane.ERROR_MESSAGE);
//				}
//				else if(id_T.getText().matches("[a-zA-z가-힣]+")) {
//					JOptionPane.showMessageDialog(null, "올바른 ID 형식이 아닙니다. 숫자만 입력하세요.", "ID 등록 실패", JOptionPane.ERROR_MESSAGE);
//				}
//				else if(id_T.getText().length() != 7) {
//					JOptionPane.showMessageDialog(null, "ID 7자리를 입력해주세요", "ID 등록 실패", JOptionPane.ERROR_MESSAGE);	
//				}
//				else if(Integer.parseInt(score_T.getText()) < 0 || Integer.parseInt(score_T.getText()) > 100) {
//					JOptionPane.showMessageDialog(null, "점수는 0~100사이 숫자만 입력가능합니다.", "점수 등록 실패", JOptionPane.ERROR_MESSAGE);
//			    }
//				
//				else if(id_T.getText().equals(id_T.getText())) {  //dao.read().equals(id_T)
//					JOptionPane.showMessageDialog(null, "중복된 ID 입니다.", "ID 등록 실패", JOptionPane.ERROR_MESSAGE);
//				}
//			}
//			id_T.setText("");
//			name_T.setText("");
//			score_T.setText("");
//			view_B.setEnabled(true);
//			regist_B.setEnabled(false);
//			modify_B.setEnabled(true);
//			delete_B.setEnabled(true);
//		}
		
		else if (e.getActionCommand().equals("수정")) {
		    String id_T = JOptionPane.showInputDialog("수정할 학생의 ID를 입력하세요");
		    if (id_T == null) {
		        JOptionPane.showMessageDialog(null, "취소하셨습니다.", "취소", JOptionPane.INFORMATION_MESSAGE);
		        return;
		    }
		    Student student = dao.getStudentById(id_T);
		    if (student != null) {
		        String newName = JOptionPane.showInputDialog("새로운 이름을 입력하세요");
		        int newScore = Integer.parseInt(JOptionPane.showInputDialog("새로운 성적을 입력하세요"));
		        if (!newName.matches("[a-zA-Z가-힣]+")) {
		            JOptionPane.showMessageDialog(null, "올바른 이름 형식이 아닙니다.", "이름 수정 실패", JOptionPane.ERROR_MESSAGE);
		        } else if (newScore < 0 || newScore > 100) {
		            JOptionPane.showMessageDialog(null, "성적은 0~100 사이의 숫자만 입력 가능합니다.", "성적 수정 실패", JOptionPane.ERROR_MESSAGE);
		        } else {
		            student.setName(newName);
		            student.setScore(newScore);
		            JOptionPane.showMessageDialog(null, "이름과 성적이 수정되었습니다.", "", JOptionPane.INFORMATION_MESSAGE);
		            check_T.setText(dao.read());
		        }
		    } else {
		        JOptionPane.showMessageDialog(null, "해당하는 학생의 ID가 없습니다.", "수정 실패", JOptionPane.ERROR_MESSAGE);
		    }
		}

//		else if(e.getActionCommand().equals("수정")) {
//		    String id_T = JOptionPane.showInputDialog("수정할 학생의 ID를 입력하세요");
//		    Student student = dao.getStudentById(id_T);
//		    if (student != null) {
//		        String newName = JOptionPane.showInputDialog("새로운 이름을 입력하세요");
//		        int newScore = Integer.parseInt(JOptionPane.showInputDialog("새로운 성적을 입력하세요"));
//		        if (!newName.matches("[a-zA-Z가-힣]+")) {
//		            JOptionPane.showMessageDialog(null, "올바른 이름 형식이 아닙니다.", "이름 수정 실패", JOptionPane.ERROR_MESSAGE);
//		        } else if (newScore < 0 || newScore > 100) {
//		            JOptionPane.showMessageDialog(null, "점수는 0~100 사이의 숫자만 입력 가능합니다.", "성적 수정 실패", JOptionPane.ERROR_MESSAGE);
//		        } else {
//		            student.setName(newName);
//		            student.setScore(newScore);
//		            JOptionPane.showMessageDialog(null, "이름과 성적이 수정되었습니다.", "", JOptionPane.INFORMATION_MESSAGE);
//		        }
//		    } else {
//		        JOptionPane.showMessageDialog(null, "해당하는 학생의 ID가 없습니다.", "수정 실패", JOptionPane.ERROR_MESSAGE);
//		    }
//		}

		else if(e.getActionCommand().equals("삭제")) {
			String id_T = JOptionPane.showInputDialog("삭제할 학생의 ID를 입력하세요");
			if (id_T == null) {
		        JOptionPane.showMessageDialog(null, "취소하셨습니다.", "취소", JOptionPane.INFORMATION_MESSAGE);
		        return;
		    }
		    Student student = dao.getStudentById(id_T);
		    if (student != null) {
			    JOptionPane.showMessageDialog(null, "삭제되었습니다.", "", JOptionPane.INFORMATION_MESSAGE);
			    dao.delete(id_T);
		    }
		    else {
		    	JOptionPane.showMessageDialog(null, "입력한 ID가 존재하지 않습니다.", "경고", JOptionPane.WARNING_MESSAGE);
		    }
//			String id_T = JOptionPane.showInputDialog("ID를 입력하세요");
//			dao.delete(id_T);
//			try {
//				if(id_T != null) {
//					JOptionPane.showMessageDialog(null, "입력한 ID가 존재하지 않습니다.", "경고", JOptionPane.WARNING_MESSAGE);
//			        return;
//				}
//				else {
//					JOptionPane.showMessageDialog(null, "ID가 삭제되었습니다.", "", JOptionPane.INFORMATION_MESSAGE);
//				}
//			}catch(Exception e4) {
//				JOptionPane.showMessageDialog(null, "취소하셨습니다.", "경고", JOptionPane.WARNING_MESSAGE);
//				return;
//			}
		}
//		OPEN 눌렀을 때
		else if(e.getActionCommand().equals("OPEN")) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT&txt", "txt");
			fc.setFileFilter(filter);
			fc.setCurrentDirectory(new File(".\\"));
			int ret = fc.showOpenDialog(null);
			if(ret != JFileChooser.APPROVE_OPTION) {		//열기 버튼을 누르지 않은 경우
				JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			String filePath = fc.getSelectedFile().getPath();
			try {
				BufferedReader br = new BufferedReader(new FileReader(filePath));
				check_T.setText("");
				String str;
				while ((str = br.readLine()) != null) {
					check_T.append(str + '\n');
		        }
				br.close();
			}catch(Exception e3){
				JOptionPane.showMessageDialog(this, "읽기오류");
			}
		}
//		SAVE 눌렀을 때
		else if(e.getActionCommand().equals("SAVE")) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT&txt", "txt");
			fc.setFileFilter(filter);
			int ret = fc.showSaveDialog(null);		//저장 파일다이얼로그출력
			if(ret != JFileChooser.APPROVE_OPTION) {		//저장 버튼을 누르지 않은 경우
				JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
//			사용자가 저장하기 위한 메뉴를 선택
			String filePath = fc.getSelectedFile().getPath() + ".txt";
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
				bw.write(check_T.getText());
				JOptionPane.showMessageDialog(null, "저장되었습니다.", "", JOptionPane.INFORMATION_MESSAGE);
				bw.close();
			}catch(Exception e2){
				JOptionPane.showMessageDialog(this, "저장오류");
			}
		}
		else if(e.getActionCommand().equals("조회")) {
			check_T.setText(dao.read());
		}
		else if(e.getActionCommand().equals("이름오름차순정렬")) {
			dao.sortAsc_name();
			check_T.setText(dao.read());
		}
		else if(e.getActionCommand().equals("이름내림차순정렬")) {
			dao.sortDesc_name();
			check_T.setText(dao.read());
		}
		else if(e.getActionCommand().equals("성적오름차순정렬")) {
			dao.sortAsc_score();
			check_T.setText(dao.read());
		}
		else if(e.getActionCommand().equals("성적내림차순정렬")) {
			dao.sortDesc_score();
			check_T.setText(dao.read());			
		}
		else if(e.getActionCommand().equals("ID오름차순정렬")) {
			dao.sortAsc_ID();
			check_T.setText(dao.read());			
		}
		else if(e.getActionCommand().equals("ID내림차순정렬")) {
			dao.sortDesc_ID();
			check_T.setText(dao.read());			
		}
		else if(e.getActionCommand().equals("종료")){
			int result = JOptionPane.showConfirmDialog(null, "종료를 원하십니까?", "종료버튼", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource().equals(id_T)) {
			output_T.setText("ID를 입력해주세요" + "\n");
		}
		else if(e.getSource().equals(name_T)) {
			output_T.setText("이름을 입력해주세요" + "\n");
		}
		else if(e.getSource().equals(score_T)){
			output_T.setText("성적을 입력해주세요" + "\n");
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource().equals(id_T)) {
			if(id_T.getText().equals("")) 
				output_T.setText("ID가 없습니다.");
			else
				name_T.setEditable(true);
		}
		else if(e.getSource().equals(name_T)) {
			if(name_T.getText().equals("")) 
				output_T.setText("이름이 없습니다.");
			else
				score_T.setEditable(true);
		}
		else if(e.getSource().equals(score_T)){
			if(score_T.getText().equals("")) 
				output_T.setText("성적이 없습니다.");
			else
				regist_B.setEnabled(true);
				regist_B.requestFocus();
		}
	}
}