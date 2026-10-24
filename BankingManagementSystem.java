import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BankingManagementSystem extends JFrame {

    // ============================================================
    // SALONI FINANCE BANK - EDUCATIONAL BANKING MANAGEMENT SYSTEM
    // ============================================================

    static class Customer {
        int id;
        String name, accountNo, accountType, phone, email, city, status;
        double balance;
        LocalDateTime created;

        Customer(int id, String name, String accountNo, String accountType,
                 String phone, String email, String city, double balance) {
            this.id = id;
            this.name = name;
            this.accountNo = accountNo;
            this.accountType = accountType;
            this.phone = phone;
            this.email = email;
            this.city = city;
            this.balance = balance;
            this.status = "Active";
            this.created = LocalDateTime.now();
        }
    }

    static class Transaction {
        String ref, type, fromAccount, toAccount, note, date, status;
        double amount;

        Transaction(String ref, String type, String fromAccount, String toAccount,
                    double amount, String note, String status) {
            this.ref = ref;
            this.type = type;
            this.fromAccount = fromAccount;
            this.toAccount = toAccount;
            this.amount = amount;
            this.note = note;
            this.status = status;
            this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        }
    }

    static class Loan {
        String applicationId, accountNo, type, status, date;
        double amount;

        Loan(String applicationId, String accountNo, String type, double amount) {
            this.applicationId = applicationId;
            this.accountNo = accountNo;
            this.type = type;
            this.amount = amount;
            this.status = "Under Review";
            this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
    }

    private final List<Customer> customers = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();

    private JPanel contentPanel;
    private JLabel totalBalanceLabel, customersLabel, transactionsLabel, loansLabel;
    private JLabel pageTitleLabel;
    private JTable currentTable;

    private final Color NAVY = new Color(13, 32, 58);
    private final Color NAVY2 = new Color(22, 52, 91);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color SKY = new Color(59, 130, 246);
    private final Color GREEN = new Color(16, 145, 92);
    private final Color RED = new Color(220, 68, 68);
    private final Color ORANGE = new Color(234, 136, 32);
    private final Color PURPLE = new Color(124, 77, 190);
    private final Color BG = new Color(243, 246, 250);
    private final Color TEXT = new Color(31, 41, 55);
    private final DecimalFormat money = new DecimalFormat("#,##0.00");

    public BankingManagementSystem() {
        setTitle("Saloni Finance Bank | Core Banking Management System");
        setSize(1360, 820);
        setMinimumSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createDefaultCustomers();
        createDefaultTransactions();

        buildUI();
        showDashboard();
    }

    // ---------------- DEFAULT DATA ----------------

    private void createDefaultCustomers() {
        String[] first = {
                "Aarav","Aditi","Aditya","Akash","Aman","Ananya","Ankit","Anjali","Arjun","Ayush",
                "Bhavna","Chandan","Deepak","Diya","Gaurav","Isha","Karan","Kavya","Krishna","Manish",
                "Meera","Mohit","Nandini","Neha","Nikhil","Pankaj","Pooja","Prakash","Priya","Rahul",
                "Raj","Riya","Rohit","Sakshi","Saloni","Sanjay","Shivam","Shreya","Siddharth","Simran",
                "Sonia","Sourav","Tanvi","Varun","Vikas","Vikram","Vishal","Yash","Zoya","Abhishek"
        };
        String[] last = {
                "Kumar","Singh","Sharma","Verma","Gupta","Yadav","Jha","Mishra","Sinha","Das",
                "Roy","Choudhary","Prasad","Thakur","Pandey","Tiwari","Mehta","Sah","Agarwal","Rana"
        };
        String[] cities = {"Patna","Begusarai","Araria","Purnia","Muzaffarpur","Bhagalpur","Darbhanga","Gaya","Ranchi","Kolkata"};
        Random r = new Random(20260909L);

        for (int i = 1; i <= 100; i++) {
            String name = first[r.nextInt(first.length)] + " " + last[r.nextInt(last.length)];
            String type = (i % 5 == 0) ? "Current" : "Savings";
            String accountNo = "SFB" + String.format("%09d", 100000000 + i);
            String phone = "9" + String.format("%09d", r.nextInt(1000000000));
            String email = name.toLowerCase().replace(" ", ".") + i + "@salonifinance.bank";
            String city = cities[r.nextInt(cities.length)];

            // ₹10,000 to ₹2.5 crore
            double balance = 10000 + (r.nextInt(24990000) + r.nextDouble());
            balance = Math.round(balance * 100.0) / 100.0;

            customers.add(new Customer(i, name, accountNo, type, phone, email, city, balance));
        }
    }

    private void createDefaultTransactions() {
        Random r = new Random(7788);
        for (int i = 1; i <= 35; i++) {
            Customer c = customers.get(r.nextInt(customers.size()));
            transactions.add(new Transaction(
                    "SFB-TXN-" + String.format("%06d", i),
                    "Opening / Credit",
                    c.accountNo, "-", 1000 + r.nextInt(50000),
                    "Initial system transaction", "SUCCESS"
            ));
        }
    }

    // ---------------- MAIN UI ----------------

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createSidebar(), BorderLayout.WEST);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BG);
        root.add(contentPanel, BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 225, 232)));
        header.setPreferredSize(new Dimension(1000, 82));

        JPanel brand = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        brand.setOpaque(false);

        JLabel logo = new JLabel("SFB");
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setBackground(NAVY);
        logo.setOpaque(true);
        logo.setFont(new Font("SansSerif", Font.BOLD, 20));
        logo.setPreferredSize(new Dimension(52, 52));

        JPanel names = new JPanel();
        names.setOpaque(false);
        names.setLayout(new BoxLayout(names, BoxLayout.Y_AXIS));

        JLabel bank = new JLabel("SALONI FINANCE BANK");
        bank.setFont(new Font("SansSerif", Font.BOLD, 20));
        bank.setForeground(NAVY);

        JLabel sub = new JLabel("Secure • Simple • Smart Banking");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sub.setForeground(new Color(100, 116, 139));

        names.add(bank);
        names.add(sub);
        brand.add(logo);
        brand.add(names);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        right.setOpaque(false);

        JLabel online = new JLabel("● SYSTEM ONLINE");
        online.setForeground(GREEN);
        online.setFont(new Font("SansSerif", Font.BOLD, 12));

        JLabel admin = new JLabel("Administrator  ▾");
        admin.setForeground(TEXT);
        admin.setFont(new Font("SansSerif", Font.BOLD, 13));

        right.add(online);
        right.add(admin);

        header.add(brand, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    private JPanel createSidebar() {
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBackground(NAVY);
        side.setPreferredSize(new Dimension(225, 700));
        side.setBorder(new EmptyBorder(18, 12, 18, 12));

        JLabel menu = new JLabel("MAIN MENU");
        menu.setForeground(new Color(150, 170, 195));
        menu.setFont(new Font("SansSerif", Font.BOLD, 11));
        menu.setBorder(new EmptyBorder(0, 10, 10, 0));
        side.add(menu);

        addMenu(side, "▣   Dashboard", e -> showDashboard());
        addMenu(side, "▤   Accounts", e -> showAccounts());
        addMenu(side, "♙   Customers", e -> showCustomers());
        addMenu(side, "＋   Deposit / Credit", e -> depositDialog());
        addMenu(side, "−   Withdraw / Debit", e -> withdrawDialog());
        addMenu(side, "⇄   Money Transfer", e -> transferDialog());
        addMenu(side, "◷   Transactions", e -> showTransactions());
        addMenu(side, "▣   Loans", e -> loanDialog());
        addMenu(side, "▥   Reports", e -> showReports());

        side.add(Box.createVerticalGlue());

        JLabel security = new JLabel("<html><div style='padding:8px'>"
                + "<b>Security</b><br>Demo environment<br>Data stored in memory</div></html>");
        security.setForeground(new Color(190, 205, 225));
        security.setFont(new Font("SansSerif", Font.PLAIN, 11));
        side.add(security);

        JButton logout = menuButton("⎋   Exit System", RED);
        logout.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Close Saloni Finance Bank system?",
                    "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) System.exit(0);
        });
        side.add(logout);

        return side;
    }

    private void addMenu(JPanel panel, String text, java.awt.event.ActionListener action) {
        JButton b = menuButton(text, NAVY2);
        b.addActionListener(action);
        panel.add(b);
        panel.add(Box.createVerticalStrut(6));
    }

    private JButton menuButton(String text, Color color) {
        JButton b = new JButton(text);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(205, 43));
        b.setPreferredSize(new Dimension(205, 43));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(new EmptyBorder(0, 14, 0, 8));
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        return b;
    }

    // ---------------- DASHBOARD ----------------

    private void showDashboard() {
        contentPanel.removeAll();

        JPanel page = pagePanel();
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JPanel titleBox = new JPanel();
        titleBox.setOpaque(false);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));

        pageTitleLabel = new JLabel("Dashboard");
        pageTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 29));
        pageTitleLabel.setForeground(TEXT);

        JLabel desc = new JLabel("Overview of Saloni Finance Bank core banking operations");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        desc.setForeground(new Color(100, 116, 139));

        titleBox.add(pageTitleLabel);
        titleBox.add(Box.createVerticalStrut(4));
        titleBox.add(desc);

        JButton quick = primaryButton("＋ New Account");
        quick.addActionListener(e -> openAccountDialog());

        top.add(titleBox, BorderLayout.WEST);
        top.add(quick, BorderLayout.EAST);

        page.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JPanel cards = new JPanel(new GridLayout(1, 4, 15, 15));
        cards.setOpaque(false);

        totalBalanceLabel = new JLabel();
        customersLabel = new JLabel();
        transactionsLabel = new JLabel();
        loansLabel = new JLabel();

        cards.add(statCard("TOTAL DEPOSITS", totalBalanceLabel, GREEN, "₹"));
        cards.add(statCard("CUSTOMERS", customersLabel, BLUE, "◉"));
        cards.add(statCard("TRANSACTIONS", transactionsLabel, PURPLE, "↕"));
        cards.add(statCard("LOAN APPLICATIONS", loansLabel, ORANGE, "▣"));

        center.add(Box.createVerticalStrut(22));
        center.add(cards);
        center.add(Box.createVerticalStrut(18));

        JPanel lower = new JPanel(new GridLayout(1, 2, 15, 15));
        lower.setOpaque(false);
        lower.add(recentTransactionsPanel());
        lower.add(quickActionsPanel());

        center.add(lower);

        page.add(center, BorderLayout.CENTER);
        refreshDashboard();

        contentPanel.add(page);
        refresh();
    }

    private JPanel pagePanel() {
        JPanel p = new JPanel(new BorderLayout(0, 0));
        p.setBackground(BG);
        p.setBorder(new EmptyBorder(24, 24, 24, 24));
        return p;
    }

    private JPanel statCard(String title, JLabel value, Color accent, String icon) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 237)),
                new EmptyBorder(16, 18, 16, 18)));

        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 11));
        t.setForeground(new Color(100, 116, 139));

        value.setFont(new Font("SansSerif", Font.BOLD, 21));
        value.setForeground(TEXT);

        JLabel ic = new JLabel(icon);
        ic.setHorizontalAlignment(SwingConstants.CENTER);
        ic.setForeground(accent);
        ic.setFont(new Font("SansSerif", Font.BOLD, 24));
        ic.setPreferredSize(new Dimension(42, 42));

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(t, BorderLayout.NORTH);
        center.add(value, BorderLayout.CENTER);

        card.add(center, BorderLayout.CENTER);
        card.add(ic, BorderLayout.EAST);
        return card;
    }

    private JPanel recentTransactionsPanel() {
        JPanel panel = whitePanel("Recent Transactions");

        String[] cols = {"Reference", "Type", "Account", "Amount", "Status"};
        DefaultTableModel m = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        int start = Math.max(0, transactions.size() - 7);
        for (int i = transactions.size() - 1; i >= start; i--) {
            Transaction t = transactions.get(i);
            m.addRow(new Object[]{t.ref, t.type, t.fromAccount, "₹ " + money.format(t.amount), t.status});
        }

        JTable table = styledTable(m);
        table.setRowHeight(32);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel quickActionsPanel() {
        JPanel panel = whitePanel("Quick Banking Actions");
        JPanel grid = new JPanel(new GridLayout(2, 2, 12, 12));
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton a = actionButton("＋  Open Account", BLUE);
        JButton b = actionButton("₹  Deposit Money", GREEN);
        JButton c = actionButton("↕  Withdraw Money", RED);
        JButton d = actionButton("⇄  Transfer Funds", PURPLE);

        a.addActionListener(e -> openAccountDialog());
        b.addActionListener(e -> depositDialog());
        c.addActionListener(e -> withdrawDialog());
        d.addActionListener(e -> transferDialog());

        grid.add(a); grid.add(b); grid.add(c); grid.add(d);
        panel.add(grid, BorderLayout.CENTER);
        return panel;
    }

    private JPanel whitePanel(String title) {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 237)),
                new EmptyBorder(15, 15, 15, 15)));
        JLabel l = new JLabel(title);
        l.setFont(new Font("SansSerif", Font.BOLD, 16));
        l.setForeground(TEXT);
        p.add(l, BorderLayout.NORTH);
        return p;
    }

    private JButton actionButton(String text, Color c) {
        JButton b = new JButton(text);
        b.setBackground(new Color(248, 250, 252));
        b.setForeground(c);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(220, 226, 234)));
        return b;
    }

    private JButton primaryButton(String text) {
        return createButton(text, BLUE);
    }

    private JButton createButton(String text, Color c) {
        JButton b = new JButton(text);
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(10, 18, 10, 18));
        return b;
    }

    private void refreshDashboard() {
        double total = 0;
        for (Customer c : customers) total += c.balance;
        totalBalanceLabel.setText("₹ " + money.format(total));
        customersLabel.setText(String.valueOf(customers.size()));
        transactionsLabel.setText(String.valueOf(transactions.size()));
        loansLabel.setText(String.valueOf(loans.size()));
    }

    // ---------------- ACCOUNTS ----------------

    private void showAccounts() {
        contentPanel.removeAll();
        JPanel page = pagePanel();

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JLabel title = sectionTitle("Account Management", "Manage savings and current accounts");
        JButton add = primaryButton("＋ Open New Account");
        add.addActionListener(e -> openAccountDialog());
        top.add(title, BorderLayout.WEST);
        top.add(add, BorderLayout.EAST);
        page.add(top, BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(0, 12));
        body.setOpaque(false);

        JTextField search = new JTextField();
        search.putClientProperty("JTextField.placeholderText", "Search account, name, phone or city...");
        search.setPreferredSize(new Dimension(350, 38));
        body.add(search, BorderLayout.NORTH);

        String[] cols = {"Account No.", "Customer", "Type", "Phone", "City", "Balance", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        populateCustomerTable(model, "");
        JTable table = styledTable(model);
        currentTable = table;
        body.add(new JScrollPane(table), BorderLayout.CENTER);

        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            void update() {
                model.setRowCount(0);
                populateCustomerTable(model, search.getText().trim());
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
        });

        page.add(body, BorderLayout.CENTER);
        contentPanel.add(page);
        refresh();
    }

    private void populateCustomerTable(DefaultTableModel model, String query) {
        String q = query.toLowerCase();
        for (Customer c : customers) {
            String all = (c.accountNo + " " + c.name + " " + c.phone + " " + c.city).toLowerCase();
            if (q.isEmpty() || all.contains(q)) {
                model.addRow(new Object[]{
                        c.accountNo, c.name, c.accountType, c.phone, c.city,
                        "₹ " + money.format(c.balance), c.status
                });
            }
        }
    }

    private void openAccountDialog() {
        JTextField name = new JTextField();
        JTextField phone = new JTextField();
        JTextField email = new JTextField();
        JTextField city = new JTextField();
        JTextField balance = new JTextField("10000");
        JComboBox<String> type = new JComboBox<>(new String[]{"Savings", "Current"});

        JPanel form = formPanel();
        addField(form, "Customer Name *", name);
        addField(form, "Mobile Number *", phone);
        addField(form, "Email", email);
        addField(form, "City", city);
        addField(form, "Account Type", type);
        addField(form, "Opening Balance (₹) *", balance);

        int result = JOptionPane.showConfirmDialog(this, form, "Open New Bank Account",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            String n = name.getText().trim();
            double amount = Double.parseDouble(balance.getText().trim());
            if (n.isEmpty() || phone.getText().trim().isEmpty() || amount < 10000) throw new Exception();

            int id = customers.size() + 1;
            String acc = "SFB" + String.format("%09d", 100000000 + id);
            Customer c = new Customer(id, n, acc, (String) type.getSelectedItem(),
                    phone.getText().trim(), email.getText().trim(), city.getText().trim(), amount);
            customers.add(c);

            transactions.add(new Transaction(nextRef(), "Account Opening", acc, "-",
                    amount, "New account opened", "SUCCESS"));

            JOptionPane.showMessageDialog(this,
                    "Account opened successfully!\n\nBank: Saloni Finance Bank\nAccount No: " + acc +
                            "\nCustomer: " + n + "\nOpening Balance: ₹ " + money.format(amount),
                    "Account Created", JOptionPane.INFORMATION_MESSAGE);

            showAccounts();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid details. Minimum opening balance is ₹10,000.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------------- CUSTOMERS ----------------

    private void showCustomers() {
        contentPanel.removeAll();
        JPanel page = pagePanel();

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(sectionTitle("Customer Management", "Customer profile and account information"), BorderLayout.WEST);
        page.add(top, BorderLayout.NORTH);

        String[] cols = {"ID", "Name", "Account No.", "Account Type", "Phone", "Email", "City", "Balance"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        for (Customer c : customers) {
            model.addRow(new Object[]{c.id, c.name, c.accountNo, c.accountType, c.phone,
                    c.email, c.city, "₹ " + money.format(c.balance)});
        }

        JTable table = styledTable(model);
        page.add(new JScrollPane(table), BorderLayout.CENTER);
        contentPanel.add(page);
        refresh();
    }

    // ---------------- DEPOSIT ----------------

    private void depositDialog() {
        Customer c = chooseCustomer("Deposit / Credit");
        if (c == null) return;

        JTextField amount = new JTextField();
        JTextField note = new JTextField("Cash deposit");
        JPanel form = formPanel();
        addField(form, "Account", new JLabel(c.accountNo + " - " + c.name));
        addField(form, "Amount (₹)", amount);
        addField(form, "Narration", note);

        int result = JOptionPane.showConfirmDialog(this, form, "Credit Money",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            double a = Double.parseDouble(amount.getText().trim());
            if (a <= 0) throw new Exception();
            c.balance += a;
            transactions.add(new Transaction(nextRef(), "Credit", c.accountNo, "-",
                    a, note.getText(), "SUCCESS"));
            showReceipt("CREDIT SUCCESSFUL", c, a, "Credit", note.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter a valid positive amount.",
                    "Invalid Amount", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------------- WITHDRAW ----------------

    private void withdrawDialog() {
        Customer c = chooseCustomer("Withdraw / Debit");
        if (c == null) return;

        JTextField amount = new JTextField();
        JTextField note = new JTextField("Cash withdrawal");
        JPanel form = formPanel();
        addField(form, "Account", new JLabel(c.accountNo + " - " + c.name));
        addField(form, "Amount (₹)", amount);
        addField(form, "Narration", note);

        int result = JOptionPane.showConfirmDialog(this, form, "Debit Money",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            double a = Double.parseDouble(amount.getText().trim());
            if (a <= 0 || a > c.balance) {
                JOptionPane.showMessageDialog(this, "Insufficient balance or invalid amount.",
                        "Transaction Declined", JOptionPane.ERROR_MESSAGE);
                return;
            }
            c.balance -= a;
            transactions.add(new Transaction(nextRef(), "Debit", c.accountNo, "-",
                    a, note.getText(), "SUCCESS"));
            showReceipt("DEBIT SUCCESSFUL", c, a, "Debit", note.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter a valid amount.",
                    "Invalid Amount", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------------- TRANSFER ----------------

    private void transferDialog() {
        Customer from = chooseCustomer("Select Sender Account");
        if (from == null) return;
        Customer to = chooseCustomer("Select Receiver Account");
        if (to == null) return;
        if (from == to) {
            JOptionPane.showMessageDialog(this, "Sender and receiver cannot be the same account.");
            return;
        }

        JTextField amount = new JTextField();
        JTextField note = new JTextField("Fund transfer");
        JPanel form = formPanel();
        addField(form, "From", new JLabel(from.accountNo + " - " + from.name));
        addField(form, "To", new JLabel(to.accountNo + " - " + to.name));
        addField(form, "Amount (₹)", amount);
        addField(form, "Narration", note);

        int result = JOptionPane.showConfirmDialog(this, form, "Money Transfer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            double a = Double.parseDouble(amount.getText().trim());
            if (a <= 0 || a > from.balance) {
                JOptionPane.showMessageDialog(this, "Transfer declined: insufficient balance.",
                        "Transaction Declined", JOptionPane.ERROR_MESSAGE);
                return;
            }

            from.balance -= a;
            to.balance += a;
            String ref = nextRef();
            transactions.add(new Transaction(ref, "Transfer", from.accountNo, to.accountNo,
                    a, note.getText(), "SUCCESS"));

            JOptionPane.showMessageDialog(this,
                    "TRANSFER SUCCESSFUL\n\nReference: " + ref +
                            "\nFrom: " + from.accountNo +
                            "\nTo: " + to.accountNo +
                            "\nAmount: ₹ " + money.format(a) +
                            "\nNew Balance: ₹ " + money.format(from.balance),
                    "Transaction Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter a valid amount.",
                    "Invalid Amount", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Customer chooseCustomer(String title) {
        JComboBox<String> box = new JComboBox<>();
        for (Customer c : customers)
            box.addItem(c.accountNo + " | " + c.name + " | ₹" + money.format(c.balance));

        int result = JOptionPane.showConfirmDialog(this, box, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return null;

        return customers.get(box.getSelectedIndex());
    }

    private void showReceipt(String heading, Customer c, double amount, String type, String note) {
        String ref = transactions.get(transactions.size() - 1).ref;
        String receipt = "SALONI FINANCE BANK\n"
                + "----------------------------------------\n"
                + heading + "\n"
                + "Reference : " + ref + "\n"
                + "Account   : " + c.accountNo + "\n"
                + "Customer  : " + c.name + "\n"
                + "Type      : " + type + "\n"
                + "Amount    : ₹ " + money.format(amount) + "\n"
                + "Narration : " + note + "\n"
                + "Balance   : ₹ " + money.format(c.balance) + "\n"
                + "Status    : SUCCESS\n"
                + "----------------------------------------\n"
                + "Thank you for banking with SFB.";
        JOptionPane.showMessageDialog(this, receipt, "Transaction Receipt",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------------- TRANSACTIONS ----------------

    private void showTransactions() {
        contentPanel.removeAll();
        JPanel page = pagePanel();

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(sectionTitle("Transaction History", "Complete banking transaction ledger"), BorderLayout.WEST);
        page.add(top, BorderLayout.NORTH);

        String[] cols = {"Date", "Reference", "Type", "From Account", "To Account", "Amount", "Status", "Narration"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            model.addRow(new Object[]{t.date, t.ref, t.type, t.fromAccount, t.toAccount,
                    "₹ " + money.format(t.amount), t.status, t.note});
        }

        JTable table = styledTable(model);
        page.add(new JScrollPane(table), BorderLayout.CENTER);
        contentPanel.add(page);
        refresh();
    }

    // ---------------- LOANS ----------------

    private void loanDialog() {
        JComboBox<String> type = new JComboBox<>(new String[]{
                "Home Loan", "Education Loan", "Vehicle Loan", "Personal Loan", "Business Loan"
        });
        Customer c = chooseCustomer("Select Loan Applicant");
        if (c == null) return;

        JTextField amount = new JTextField();
        JPanel form = formPanel();
        addField(form, "Applicant", new JLabel(c.name + " (" + c.accountNo + ")"));
        addField(form, "Loan Type", type);
        addField(form, "Loan Amount (₹)", amount);

        int result = JOptionPane.showConfirmDialog(this, form, "Loan Application",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            double a = Double.parseDouble(amount.getText().trim());
            if (a <= 0) throw new Exception();
            String id = "SFB-LOAN-" + String.format("%05d", loans.size() + 1);
            loans.add(new Loan(id, c.accountNo, (String) type.getSelectedItem(), a));

            JOptionPane.showMessageDialog(this,
                    "Loan application submitted.\n\nApplication ID: " + id +
                            "\nApplicant: " + c.name +
                            "\nType: " + type.getSelectedItem() +
                            "\nAmount: ₹ " + money.format(a) +
                            "\nStatus: Under Review",
                    "Loan Application", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter a valid loan amount.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------------- REPORTS ----------------

    private void showReports() {
        double total = 0, savings = 0, current = 0;
        int active = 0;

        for (Customer c : customers) {
            total += c.balance;
            if ("Savings".equals(c.accountType)) savings += c.balance;
            else current += c.balance;
            if ("Active".equals(c.status)) active++;
        }

        String report = "SALONI FINANCE BANK - MANAGEMENT REPORT\n\n"
                + "Total Customers      : " + customers.size() + "\n"
                + "Active Accounts      : " + active + "\n"
                + "Savings Deposits     : ₹ " + money.format(savings) + "\n"
                + "Current Deposits     : ₹ " + money.format(current) + "\n"
                + "Total Deposits       : ₹ " + money.format(total) + "\n"
                + "Transactions          : " + transactions.size() + "\n"
                + "Loan Applications    : " + loans.size() + "\n"
                + "System Status        : ONLINE\n\n"
                + "Note: This is an educational desktop banking simulation.";

        JTextArea area = new JTextArea(report, 20, 55);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area),
                "Bank Report", JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------------- HELPERS ----------------

    private JPanel formPanel() {
        JPanel p = new JPanel(new GridLayout(0, 2, 10, 10));
        p.setBorder(new EmptyBorder(12, 12, 12, 12));
        return p;
    }

    private void addField(JPanel p, String label, Component component) {
        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        p.add(l);
        p.add(component);
    }

    private JLabel sectionTitle(String title, String subtitle) {
        JLabel l = new JLabel("<html><div style='font-size:20px;font-weight:bold'>" +
                title + "</div><div style='font-size:11px;color:#64748b'>" +
                subtitle + "</div></html>");
        return l;
    }

    private JTable styledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setForeground(TEXT);
        table.setGridColor(new Color(230, 234, 240));
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(TEXT);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setBackground(NAVY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(100, 34));
        return table;
    }

    private String nextRef() {
        return "SFB-TXN-" + String.format("%06d", transactions.size() + 1001);
    }

    private void refresh() {
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            BankingManagementSystem app = new BankingManagementSystem();
            app.setVisible(true);
        });
    }
}