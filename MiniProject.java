import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class MiniProject extends JFrame {

    // Declaring GUI variables
    private JFrame mainFrame;
    private JPanel investorPanel;
    private JLabel investorName;
    private JTextArea investorDetails;
    private JPanel sharePanel;
    private List shareList;
    private JTextArea shareInfo;
    private JPanel sharePanelMenu;
    private JButton buyShare;
    private JButton sellShare;
    private JPanel currentStatus;
    private JTextArea currentNews;
    private ShareInit shareInit;
    private Investor inv1;

    public MiniProject() {
        File investorData = new File("inv1.ser");
        File shareData = new File("share.ser");

        // Check whether investor object already exists or not
        if(investorData.exists()) {
            try {
                FileInputStream loadData = new FileInputStream(investorData);
                ObjectInputStream ois = new ObjectInputStream(loadData);
                inv1 = (Investor) ois.readObject();
                loadData.close();
                ois.close();
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "An error occurred", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            try {
                String name = JOptionPane.showInputDialog(null, "What is your name?", "Creating new investor", JOptionPane.QUESTION_MESSAGE);
                double capital = Double.parseDouble(JOptionPane.showInputDialog(null, "What is your investor capital?", "Creating new investor", JOptionPane.QUESTION_MESSAGE));
                inv1 = new Investor(name, capital);
            }
            catch(NullPointerException exc) {
                JOptionPane.showMessageDialog(null, "You must enter details in order to proceed", "File Not Found", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "New investor " + inv1.getInvestorName() + " with balance: " + inv1.getInvestorCapital() + " has been successfully created!", "Investor Created", JOptionPane.INFORMATION_MESSAGE);
            try {
                FileOutputStream saveFile = new FileOutputStream(investorData);
                ObjectOutputStream oos = new ObjectOutputStream(saveFile);
                oos.writeObject(inv1);
                saveFile.close();
                oos.close();
            }
            catch(Exception exc) {
                JOptionPane.showMessageDialog(null, "An error occurred", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Check whether shareinit objects already exists or not
        if(shareData.exists()) {
            try {
                FileInputStream loadData2 = new FileInputStream(shareData);
                ObjectInputStream ois2 = new ObjectInputStream(loadData2);
                shareInit = (ShareInit) ois2.readObject();
                loadData2.close();
                ois2.close();
            }
            catch(Exception exc) {
                JOptionPane.showMessageDialog(null, "An error occurred", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            shareInit = new ShareInit();
        }

        mainFrame = new JFrame();
        mainFrame.setLayout(new BorderLayout(0, 0));
        investorPanel = new JPanel();
        investorPanel.setLayout(new BorderLayout(10, 10));
        mainFrame.add(investorPanel, BorderLayout.NORTH);
        investorPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), "Investor Panel", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font(investorPanel.getFont().getName(), investorPanel.getFont().getStyle(), investorPanel.getFont().getSize()), new Color(-16777216)));
        investorName = new JLabel();
        investorName.setText(inv1.getInvestorName());
        investorPanel.add(investorName, BorderLayout.WEST);
        investorDetails = new JTextArea();
        investorDetails.setEditable(false);
        investorDetails.setText(inv1.toString());
        JScrollPane scrPane1 = new JScrollPane(investorDetails);
        scrPane1.setPreferredSize(new Dimension(800, 100));
        investorPanel.add(scrPane1, BorderLayout.CENTER);
        sharePanel = new JPanel();
        sharePanel.setLayout(new BorderLayout(0, 0));
        mainFrame.add(sharePanel, BorderLayout.CENTER);
        sharePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Share Panel", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font(sharePanel.getFont().getName(), sharePanel.getFont().getStyle(), sharePanel.getFont().getSize())));
        shareList = new List();
        for(int i = 0; i < shareInit.stocks.size(); i++) {
            shareList.add(shareInit.getShare(i).getName());
        }
        shareList.addItemListener(new ShareListListener());
        sharePanel.add(shareList, BorderLayout.WEST);
        shareInfo = new JTextArea();
        shareInfo.setEditable(false);
        sharePanel.add(shareInfo, BorderLayout.CENTER);
        sharePanelMenu = new JPanel();
        sharePanelMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        sharePanel.add(sharePanelMenu, BorderLayout.EAST);
        buyShare = new JButton();
        buyShare.setText("Buy Share");
        buyShare.addActionListener(new ButtonListener());
        sharePanelMenu.add(buyShare);
        sellShare = new JButton();
        sellShare.setText("Sell Share");
        sellShare.addActionListener(new ButtonListener());
        sharePanelMenu.add(sellShare);
        currentStatus = new JPanel();
        currentStatus.setLayout(new BorderLayout(0, 0));
        sharePanel.add(currentStatus, BorderLayout.SOUTH);
        currentStatus.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), "Stock Market News"));
        currentNews = new JTextArea("Your latest actions will be shown here...\n");
        currentNews.setEditable(false);
        JScrollPane scrPane2 = new JScrollPane(currentNews);
        scrPane2.setPreferredSize(new Dimension(800, 100));
        currentStatus.add(scrPane2, BorderLayout.CENTER);
        mainFrame.addWindowListener(new CloseWindowListener());
        mainFrame.setSize(1000, 600);
        mainFrame.setTitle("Stock Market Simulation");
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new MiniProject();

    }

    private class ShareListListener implements ItemListener {
        public void itemStateChanged(ItemEvent event) {
            for(int i = 0; i < shareInit.stocks.size(); i++) {
                if(shareInit.stocks.get(i).getName().equals(shareList.getSelectedItem())) {
                    shareInfo.setText(shareInit.stocks.get(i).toString());
                    break;
                }
                else {
                    continue;
                }
            }
        }
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // If user clicks Buy Share following code executed
            if(event.getActionCommand().equals(buyShare.getText())) {
                try {
                    int amount = Integer.parseInt(JOptionPane.showInputDialog(null, "How many shares of " + shareList.getSelectedItem().toString() + " would you like to buy?", "Buy Share", JOptionPane.QUESTION_MESSAGE));
                    // For loop in order to identify selected item.
                    for(int i = 0; i < shareInit.stocks.size(); i++) {
                        // If selected item is equal to the item in the position in the array list
                        if(shareInit.stocks.get(i).getName().equals(shareList.getSelectedItem())) {
                            if ((shareInit.stocks.get(i).getValue() * amount) <= inv1.getInvestorCapital()) {
                                inv1.buyShare(shareInit.stocks.get(i).getValue(), amount, shareInit.stocks.get(i));
                                shareInit.stocks.get(i).setNumberAvailable(shareInit.getShare(i).getNumberAvailable() - amount);
                                currentNews.append("You have just bought " + amount + " share(s) of " + shareInit.stocks.get(i).getName() + "\n");
                                double prevPrice = shareInit.stocks.get(i).getValue();
                                shareInit.stocks.get(i).setMarketValue();
                                currentNews.append("The price of share " + shareInit.stocks.get(i).getName() + " changed by $" + (shareInit.stocks.get(i).getValue() - prevPrice) + "\n");
                                // Refresh GUI
                                shareInfo.setText(shareInit.stocks.get(i).toString());
                                investorDetails.setText(inv1.toString());
                                break;
                            } else {
                                JOptionPane.showMessageDialog(null, "You do not have enough money to purchase that", "Buy Share Error", JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }
                        else {
                            continue;
                        }
                    }
                }
                catch(NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "You have not selected anything", "Nothing Selected", JOptionPane.ERROR_MESSAGE);
                }
            }
            // If user clicks Sell Share this code executed
            else if(event.getActionCommand().equals(sellShare.getText())) {
                try {
                    // Enter number to sell
                    int amount = Integer.parseInt(JOptionPane.showInputDialog(null, "How many shares of " + shareList.getSelectedItem().toString() + " would you like to sell?", "Sell Share", JOptionPane.QUESTION_MESSAGE));
                    for(int i = 0; i < shareInit.stocks.size(); i++) {

                        if (shareInit.stocks.get(i).getName().equals(shareList.getSelectedItem())) {
                            if(inv1.getShareOwnedArray().contains(shareInit.stocks.get(i)) && (inv1.getNumberShareOwnedArray().get(inv1.getShareOwnedArray().indexOf(shareInit.stocks.get(i))) - amount) < 0) {
                                JOptionPane.showMessageDialog(null, "You do not have enough shares to sell", "Sell Share Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else if(inv1.getShareOwnedArray().contains(shareInit.stocks.get(i))) {
                                inv1.sellShare(shareInit.stocks.get(i).getValue(), amount, shareInit.stocks.get(i));
                                shareInit.stocks.get(i).setNumberAvailable(shareInit.getShare(i).getNumberAvailable() + amount);
                                currentNews.append("You have just sold " + amount + " share(s) of " + shareInit.stocks.get(i).getName() + "\n");
                                double prevPrice = shareInit.stocks.get(i).getValue();
                                shareInit.stocks.get(i).setMarketValue();
                                currentNews.append("The price of share " + shareInit.stocks.get(i).getName() + " changed by $" + (shareInit.stocks.get(i).getValue() - prevPrice) + "\n");
                                shareInfo.setText(shareInit.stocks.get(i).toString());
                                investorDetails.setText(inv1.toString());
                            }
                            else {
                                JOptionPane.showMessageDialog(null, "You do not own any of that share", "Sell Share Error", JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                }
                catch(NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "You have not selected anything", "Nothing Selected", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    // This class implements saving file when program is closed as well as closing the program itself.
    private class CloseWindowListener extends WindowAdapter {
        public void windowClosing(WindowEvent event) {
            try {
                File investorData = new File("inv1.ser");
                investorData.delete();
                FileOutputStream saveFile = new FileOutputStream(investorData);
                ObjectOutputStream oos = new ObjectOutputStream(saveFile);
                oos.writeObject(inv1);
                saveFile.close();
                oos.close();

                File shareData = new File("share.ser");
                shareData.delete();
                FileOutputStream saveFile2 = new FileOutputStream(shareData);
                ObjectOutputStream oos2 = new ObjectOutputStream(saveFile2);
                oos2.writeObject(shareInit);
                saveFile2.close();
                oos2.close();
                System.exit(0);
            }
            catch(Exception exc) {
                JOptionPane.showMessageDialog(null, "An error occurred", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }

}
