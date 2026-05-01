import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/* ===========================================
  MediCare Plus
 =========================================== */

/* -------------------- DATA MODELS -------------------- */
class Patient {
    String id, name, age, contact, medicalHistory;
    public Patient(String id, String name, String age, String contact, String medicalHistory) {
        this.id = id; this.name = name; this.age = age; this.contact = contact; this.medicalHistory = medicalHistory;
    }
}

class Doctor {
    String id, name, specialty, availableTime, workingDays;
    public Doctor(String id, String name, String specialty, String availableTime, String workingDays) {
        this.id = id; this.name = name; this.specialty = specialty; this.availableTime = availableTime; this.workingDays = workingDays;
    }
}

class Appointment {
    String id; Patient patient; Doctor doctor; String date; String status;
    public Appointment(String id, Patient patient, Doctor doctor, String date) {
        this.id = id; this.patient = patient; this.doctor = doctor; this.date = date; this.status = "Scheduled";
    }
}

class DataManager {
    static ArrayList<Patient> patients = new ArrayList<>();
    static ArrayList<Doctor> doctors = new ArrayList<>();
    static ArrayList<Appointment> appointments = new ArrayList<>();
}

/* -------------------- VALIDATION -------------------- */
class ValidationUtil {
    public static boolean validateFields(Component parent, JTextField... fields) {
        for (JTextField f : fields) {
            if (f.getText() == null || f.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(parent,"Please fill all fields!","Missing Data",JOptionPane.ERROR_MESSAGE);
                f.requestFocusInWindow();
                return false;
            }
        }
        return true;
    }
}

/* -------------------- UI STYLES -------------------- */
class UIStyle {
    static JButton createButton(String text, Color bg, Color fg){
        JButton btn=new JButton(text);
        btn.setFocusPainted(false); btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(bg); btn.setForeground(fg);
        btn.setBorder(new LineBorder(Color.WHITE,1,true));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){ btn.setBackground(bg.darker()); }
            public void mouseExited(MouseEvent e){ btn.setBackground(bg); }
        });
        return btn;
    }

    static JLabel createTitle(String text, int size, Color color){
        JLabel lbl=new JLabel(text,JLabel.CENTER);
        lbl.setFont(new Font("Verdana", Font.BOLD, size));
        lbl.setForeground(color);
        return lbl;
    }

    static JPanel createCard(JComponent content){
        JPanel card=new JPanel(new BorderLayout());
        card.setBackground(new Color(255,255,255,230));
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200,220,255),2,true),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));
        card.add(content);
        return card;
    }
}

/* -------------------- MAIN FRAME -------------------- */
public class MediCarePlusApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}

class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("MediCare Plus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050,720);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new GradientPanel();
        mainPanel.setLayout(cardLayout);

        // Add panels
        mainPanel.add(new DashboardPanel(this),"Dashboard");
        mainPanel.add(new PatientPanel(this),"Patients"); // Task 1
        mainPanel.add(new DoctorPanel(this),"Doctors");   // Task 2
        mainPanel.add(new AppointmentPanel(this),"Appointments"); // Tasks 3,4,5
        mainPanel.add(new ReportsPanel(this),"Reports"); // Task 6

        add(mainPanel);
        setVisible(true);
    }

    public void switchPanel(String panelName){
        cardLayout.show(mainPanel,panelName);
    }
}

/* -------------------- GRADIENT BACKGROUND -------------------- */
class GradientPanel extends JPanel {
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g;
        Color c1=new Color(135,206,250), c2=new Color(255,228,225);
        g2d.setPaint(new GradientPaint(0,0,c1,getWidth(),getHeight(),c2));
        g2d.fillRect(0,0,getWidth(),getHeight());
    }
}

/* -------------------- DASHBOARD -------------------- */
class DashboardPanel extends JPanel{
    public DashboardPanel(MainFrame frame){
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel title = UIStyle.createTitle("MediCare Plus",36,new Color(50,50,50));
        add(title,BorderLayout.NORTH);

        JPanel center=new JPanel(new GridLayout(4,1,0,30)); // 4 rows, vertical spacing 30
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(50,200,50,200)); // top,left,bottom,right padding

        // Create large horizontal buttons
        JButton pBtn = createDashboardButton("Patients", new Color(0,123,255));
        JButton dBtn = createDashboardButton("Doctors", new Color(40,167,69));
        JButton aBtn = createDashboardButton("Appointments", new Color(255,193,7));
        JButton rBtn = createDashboardButton("Reports", new Color(220,53,69));

        pBtn.addActionListener(e->frame.switchPanel("Patients"));
        dBtn.addActionListener(e->frame.switchPanel("Doctors"));
        aBtn.addActionListener(e->frame.switchPanel("Appointments"));
        rBtn.addActionListener(e->frame.switchPanel("Reports"));

        center.add(pBtn);
        center.add(dBtn);
        center.add(aBtn);
        center.add(rBtn);

        add(center,BorderLayout.CENTER);
    }

    // Custom button for dashboard
    private JButton createDashboardButton(String text, Color bg){
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 24)); // larger font
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(400,80)); // wider and taller
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE,2,true));
        btn.setOpaque(true);

        // Mouse hover effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){ btn.setBackground(bg.darker()); }
            public void mouseExited(MouseEvent e){ btn.setBackground(bg); }
        });

        return btn;
    }
}

/* ================= TASK 1: PATIENT MANAGEMENT ================= */
class PatientPanel extends JPanel{
    private JTable table; private DefaultTableModel model;

    public PatientPanel(MainFrame frame){
        setLayout(new BorderLayout(15,15)); setOpaque(false);

        JLabel title = UIStyle.createTitle("Manage Patients",28,new Color(60,60,60));
        add(title,BorderLayout.NORTH);

        String[] cols={"ID","Name","Age","Contact","Medical History"};
        model=new DefaultTableModel(cols,0){ public boolean isCellEditable(int r,int c){return false;} };
        table=new JTable(model); add(UIStyle.createCard(new JScrollPane(table)),BorderLayout.CENTER);

        JPanel bottom=new JPanel(new FlowLayout(FlowLayout.CENTER,15,15)); bottom.setOpaque(false);
        JButton addBtn = UIStyle.createButton("Add", new Color(0,123,255), Color.WHITE);
        JButton updateBtn = UIStyle.createButton("Update", new Color(23,162,184), Color.WHITE);
        JButton deleteBtn = UIStyle.createButton("Delete", new Color(220,53,69), Color.WHITE);
        JButton backBtn = UIStyle.createButton("Back", new Color(108,117,125), Color.WHITE);
        bottom.add(addBtn); bottom.add(updateBtn); bottom.add(deleteBtn); bottom.add(backBtn);
        add(bottom,BorderLayout.SOUTH);

        addBtn.addActionListener(e->showAddDialog());
        updateBtn.addActionListener(e->updatePatient());
        deleteBtn.addActionListener(e->deletePatient());
        backBtn.addActionListener(e->frame.switchPanel("Dashboard"));

        refreshTable();
    }

    private void refreshTable(){
        model.setRowCount(0);
        for(Patient p:DataManager.patients)
            model.addRow(new Object[]{p.id,p.name,p.age,p.contact,p.medicalHistory});
    }

    private void showAddDialog(){
        JTextField id=new JTextField(), name=new JTextField(), age=new JTextField(), contact=new JTextField(), history=new JTextField();
        Object[] fields={"ID:",id,"Name:",name,"Age:",age,"Contact:",contact,"Medical History:",history};
        int option = JOptionPane.showConfirmDialog(this,fields,"Add Patient",JOptionPane.OK_CANCEL_OPTION);
        if(option==JOptionPane.OK_OPTION){
            if(!ValidationUtil.validateFields(this,id,name,age,contact,history)) return;
            DataManager.patients.add(new Patient(id.getText().trim(),name.getText().trim(),age.getText().trim(),contact.getText().trim(),history.getText().trim()));
            refreshTable();
        }
    }

    private void updatePatient(){
        int row=table.getSelectedRow(); if(row==-1){ JOptionPane.showMessageDialog(this,"Select a patient to update"); return;}
        Patient p=DataManager.patients.get(row);
        JTextField id=new JTextField(p.id), name=new JTextField(p.name), age=new JTextField(p.age),
                contact=new JTextField(p.contact), history=new JTextField(p.medicalHistory);
        Object[] fields={"ID:",id,"Name:",name,"Age:",age,"Contact:",contact,"Medical History:",history};
        int option=JOptionPane.showConfirmDialog(this,fields,"Update Patient",JOptionPane.OK_CANCEL_OPTION);
        if(option==JOptionPane.OK_OPTION){
            if(!ValidationUtil.validateFields(this,id,name,age,contact,history)) return;
            p.id=id.getText().trim(); p.name=name.getText().trim(); p.age=age.getText().trim();
            p.contact=contact.getText().trim(); p.medicalHistory=history.getText().trim();
            refreshTable();
        }
    }

    private void deletePatient(){
        int row=table.getSelectedRow(); if(row==-1){ JOptionPane.showMessageDialog(this,"Select a patient to delete"); return;}
        if(JOptionPane.showConfirmDialog(this,"Delete selected patient?","Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            DataManager.patients.remove(row); refreshTable();
        }
    }
}

/* ================= TASK 2: DOCTOR MANAGEMENT ================= */
class DoctorPanel extends JPanel{
    private JTable table; private DefaultTableModel model;

    public DoctorPanel(MainFrame frame){
        setLayout(new BorderLayout(15,15)); setOpaque(false);
        JLabel title = UIStyle.createTitle("Manage Doctors",28,new Color(60,60,60));
        add(title,BorderLayout.NORTH);

        String[] cols={"ID","Name","Specialty","Available Time","Working Days"};
        model=new DefaultTableModel(cols,0){ public boolean isCellEditable(int r,int c){return false;} };
        table=new JTable(model); add(UIStyle.createCard(new JScrollPane(table)),BorderLayout.CENTER);

        JPanel bottom=new JPanel(new FlowLayout(FlowLayout.CENTER,15,15)); bottom.setOpaque(false);
        JButton addBtn = UIStyle.createButton("Add", new Color(40,167,69), Color.WHITE);
        JButton updateBtn = UIStyle.createButton("Update", new Color(23,162,184), Color.WHITE);
        JButton deleteBtn = UIStyle.createButton("Delete", new Color(220,53,69), Color.WHITE);
        JButton backBtn = UIStyle.createButton("Back", new Color(108,117,125), Color.WHITE);
        bottom.add(addBtn); bottom.add(updateBtn); bottom.add(deleteBtn); bottom.add(backBtn);
        add(bottom,BorderLayout.SOUTH);

        addBtn.addActionListener(e->showAddDialog());
        updateBtn.addActionListener(e->updateDoctor());
        deleteBtn.addActionListener(e->deleteDoctor());
        backBtn.addActionListener(e->frame.switchPanel("Dashboard"));

        refreshTable();
    }

    private void refreshTable(){
        model.setRowCount(0);
        for(Doctor d:DataManager.doctors)
            model.addRow(new Object[]{d.id,d.name,d.specialty,d.availableTime,d.workingDays});
    }

    private void showAddDialog(){
        JTextField id=new JTextField(), name=new JTextField(), spec=new JTextField(), time=new JTextField(), days=new JTextField();
        Object[] fields={"ID:",id,"Name:",name,"Specialty:",spec,"Available Time:",time,"Working Days:",days};
        int option = JOptionPane.showConfirmDialog(this,fields,"Add Doctor",JOptionPane.OK_CANCEL_OPTION);
        if(option==JOptionPane.OK_OPTION){
            if(!ValidationUtil.validateFields(this,id,name,spec,time,days)) return;
            DataManager.doctors.add(new Doctor(id.getText().trim(),name.getText().trim(),spec.getText().trim(),time.getText().trim(),days.getText().trim()));
            refreshTable();
        }
    }

    private void updateDoctor(){
        int row=table.getSelectedRow(); if(row==-1){ JOptionPane.showMessageDialog(this,"Select a doctor to update"); return;}
        Doctor d=DataManager.doctors.get(row);
        JTextField id=new JTextField(d.id), name=new JTextField(d.name), spec=new JTextField(d.specialty),
                time=new JTextField(d.availableTime), days=new JTextField(d.workingDays);
        Object[] fields={"ID:",id,"Name:",name,"Specialty:",spec,"Available Time:",time,"Working Days:",days};
        int option = JOptionPane.showConfirmDialog(this,fields,"Update Doctor",JOptionPane.OK_CANCEL_OPTION);
        if(option==JOptionPane.OK_OPTION){
            if(!ValidationUtil.validateFields(this,id,name,spec,time,days)) return;
            d.id=id.getText().trim(); d.name=name.getText().trim(); d.specialty=spec.getText().trim();
            d.availableTime=time.getText().trim(); d.workingDays=days.getText().trim();
            refreshTable();
        }
    }

    private void deleteDoctor(){
        int row=table.getSelectedRow(); if(row==-1){ JOptionPane.showMessageDialog(this,"Select a doctor to delete"); return;}
        if(JOptionPane.showConfirmDialog(this,"Delete selected doctor?","Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            DataManager.doctors.remove(row); refreshTable();
        }
    }
}

/* ================= TASK 3,4,5: APPOINTMENT MANAGEMENT ================= */
class AppointmentPanel extends JPanel{
    private JTable table; private DefaultTableModel model;

    public AppointmentPanel(MainFrame frame){
        setLayout(new BorderLayout(15,15)); setOpaque(false);
        JLabel title = UIStyle.createTitle("Manage Appointments",28,new Color(60,60,60));
        add(title,BorderLayout.NORTH);

        String[] cols={"ID","Patient","Doctor","Date","Status"};
        model=new DefaultTableModel(cols,0){ public boolean isCellEditable(int r,int c){return false;} };
        table=new JTable(model); add(UIStyle.createCard(new JScrollPane(table)),BorderLayout.CENTER);

        JPanel bottom=new JPanel(new FlowLayout(FlowLayout.CENTER,15,15)); bottom.setOpaque(false);
        JButton addBtn = UIStyle.createButton("Add", new Color(0,123,255), Color.WHITE);
        JButton updateBtn = UIStyle.createButton("Update", new Color(23,162,184), Color.WHITE);
        JButton deleteBtn = UIStyle.createButton("Delete", new Color(220,53,69), Color.WHITE);
        JButton backBtn = UIStyle.createButton("Back", new Color(108,117,125), Color.WHITE);
        bottom.add(addBtn); bottom.add(updateBtn); bottom.add(deleteBtn); bottom.add(backBtn);
        add(bottom,BorderLayout.SOUTH);

        addBtn.addActionListener(e->showAddDialog());
        updateBtn.addActionListener(e->updateAppointment());
        deleteBtn.addActionListener(e->deleteAppointment());
        backBtn.addActionListener(e->frame.switchPanel("Dashboard"));

        refreshTable();
    }

    private void refreshTable(){
        model.setRowCount(0);
        for(Appointment a:DataManager.appointments)
            model.addRow(new Object[]{a.id,a.patient.name,a.doctor.name,a.date,a.status});
    }

    /* --------- Task 5: Automatic Doctor Assignment --------- */
    private Doctor autoAssignDoctor(String specialty){
        ArrayList<Doctor> available = new ArrayList<>();
        for(Doctor d:DataManager.doctors){
            if(d.specialty.equalsIgnoreCase(specialty)) available.add(d);
        }
        if(available.isEmpty()) return null;
        // pick doctor with least appointments
        Doctor chosen = available.get(0);
        int min=10000;
        for(Doctor d:available){
            int count=0;
            for(Appointment a:DataManager.appointments){
                if(a.doctor==d) count++;
            }
            if(count<min){ min=count; chosen=d; }
        }
        return chosen;
    }

    /* --------- Task 3: SCHEDULE APPOINTMENTS --------- */
    private void showAddDialog(){
        if(DataManager.patients.isEmpty() || DataManager.doctors.isEmpty()){
            JOptionPane.showMessageDialog(this,"Add at least 1 patient and 1 doctor first!"); return;
        }
        JTextField id=new JTextField(), date=new JTextField();
        JComboBox<String> patientBox=new JComboBox<>(); for(Patient p:DataManager.patients) patientBox.addItem(p.name);
        // ask for required specialty for automatic assignment
        String specialty = JOptionPane.showInputDialog(this,"Enter required doctor specialty:");
        Doctor assigned = autoAssignDoctor(specialty);
        if(assigned==null){
            JOptionPane.showMessageDialog(this,"No doctor available for this specialty!"); return;
        }
        JComboBox<String> doctorBox=new JComboBox<>(); doctorBox.addItem(assigned.name);

        Object[] fields={"ID:",id,"Patient:",patientBox,"Doctor (assigned automatically):",doctorBox,"Date:",date};
        int option = JOptionPane.showConfirmDialog(this,fields,"Add Appointment",JOptionPane.OK_CANCEL_OPTION);
        if(option==JOptionPane.OK_OPTION){
            if(!ValidationUtil.validateFields(this,id,date)) return;
            Patient p=DataManager.patients.get(patientBox.getSelectedIndex());
            DataManager.appointments.add(new Appointment(id.getText().trim(),p,assigned,date.getText().trim()));
            refreshTable();
        }
    }

    /* --------- Task 4: TRACK APPOINTMENT STATUS --------- */
    private void updateAppointment(){
        int row=table.getSelectedRow(); if(row==-1){ JOptionPane.showMessageDialog(this,"Select appointment to update"); return;}
        Appointment a=DataManager.appointments.get(row);
        JTextField id=new JTextField(a.id), date=new JTextField(a.date);
        JComboBox<String> patientBox=new JComboBox<>(); for(Patient p:DataManager.patients) patientBox.addItem(p.name); patientBox.setSelectedItem(a.patient.name);
        JComboBox<String> doctorBox=new JComboBox<>(); for(Doctor d:DataManager.doctors) doctorBox.addItem(d.name); doctorBox.setSelectedItem(a.doctor.name);
        String[] statuses={"Scheduled","Completed","Canceled","Delayed"}; JComboBox<String> statusBox=new JComboBox<>(statuses); statusBox.setSelectedItem(a.status);

        Object[] fields={"ID:",id,"Patient:",patientBox,"Doctor:",doctorBox,"Date:",date,"Status:",statusBox};
        int option = JOptionPane.showConfirmDialog(this,fields,"Update Appointment",JOptionPane.OK_CANCEL_OPTION);
        if(option==JOptionPane.OK_OPTION){
            if(!ValidationUtil.validateFields(this,id,date)) return;
            a.id=id.getText().trim();
            a.patient=DataManager.patients.get(patientBox.getSelectedIndex());
            a.doctor=DataManager.doctors.get(doctorBox.getSelectedIndex());
            a.date=date.getText().trim();
            a.status=statusBox.getSelectedItem().toString();
            refreshTable();
        }
    }

    private void deleteAppointment(){
        int row=table.getSelectedRow(); if(row==-1){ JOptionPane.showMessageDialog(this,"Select appointment to delete"); return;}
        if(JOptionPane.showConfirmDialog(this,"Delete selected appointment?","Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            DataManager.appointments.remove(row); refreshTable();
        }
    }
}

/* ================= TASK 6: MONTHLY REPORTS ================= */
class ReportsPanel extends JPanel{
    public ReportsPanel(MainFrame frame){
        setLayout(new BorderLayout(15,15)); setOpaque(false);
        JLabel title = UIStyle.createTitle("Monthly Reports",28,new Color(60,60,60));
        add(title,BorderLayout.NORTH);

        JTextArea reportArea = new JTextArea();
        reportArea.setFont(new Font("Monospaced",Font.PLAIN,14));
        reportArea.setEditable(false);
        add(UIStyle.createCard(new JScrollPane(reportArea)),BorderLayout.CENTER);

        JPanel bottom=new JPanel(new FlowLayout(FlowLayout.CENTER,15,15)); bottom.setOpaque(false);
        JButton generateBtn = UIStyle.createButton("Generate Report", new Color(0,123,255), Color.WHITE);
        JButton backBtn = UIStyle.createButton("Back", new Color(108,117,125), Color.WHITE);
        bottom.add(generateBtn); bottom.add(backBtn);
        add(bottom,BorderLayout.SOUTH);

        generateBtn.addActionListener(e->{
            StringBuilder sb = new StringBuilder();
            sb.append("=== Monthly Report ===\n");
            sb.append("Total Appointments: ").append(DataManager.appointments.size()).append("\n\n");

            sb.append("Appointments per Doctor:\n");
            HashMap<String,Integer> docCount = new HashMap<>();
            for(Doctor d:DataManager.doctors) docCount.put(d.name,0);
            for(Appointment a:DataManager.appointments){
                docCount.put(a.doctor.name, docCount.get(a.doctor.name)+1);
            }
            for(String k:docCount.keySet()) sb.append(k).append(": ").append(docCount.get(k)).append("\n");

            sb.append("\nPatient Visits:\n");
            HashMap<String,Integer> patCount = new HashMap<>();
            for(Patient p:DataManager.patients) patCount.put(p.name,0);
            for(Appointment a:DataManager.appointments){
                patCount.put(a.patient.name, patCount.get(a.patient.name)+1);
            }
            for(String k:patCount.keySet()) sb.append(k).append(": ").append(patCount.get(k)).append("\n");

            reportArea.setText(sb.toString());
        });

        backBtn.addActionListener(e->frame.switchPanel("Dashboard"));
    }
}
