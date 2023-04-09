package ticketingsystem;

import ticketingsystem.receipts.EmailReceipt;
import ticketingsystem.receipts.PostReceipt;
import ticketingsystem.receipts.Receipt;
import ticketingsystem.receipts.SMSReceipt;

public class TicketingSystem {
    private int ticketsLeft;

    public TicketingSystem(int numTickets) {
        this.ticketsLeft = numTickets;
    }

    public static TicketingSystem getInstance(int numTickets) {
        return new TicketingSystem(numTickets);
    }

    public int getTicketsLeft() {
        return ticketsLeft;
    }

    private void setTicketsLeft(int ticketsLeft) {
        this.ticketsLeft = ticketsLeft;
    }

    public String buyTicket(String email, String sms, String address) {
        String msg = "";
        if (getTicketsLeft() > 0) {
            setTicketsLeft(getTicketsLeft() - 1);
            msg += "There is now " + getTicketsLeft() + " left!" + "\n";
            Receipt receipt = new Receipt();
            double cost = receipt.getCost();
            msg += receipt.send();
            if (email != null) {
                EmailReceipt emailReceipt = new EmailReceipt(email);
                cost += emailReceipt.getCost();
                msg += emailReceipt.send();
            }
            if (sms != null) {
                SMSReceipt smsReceipt = new SMSReceipt(sms);
                cost += smsReceipt.getCost();
                msg += smsReceipt.send();
            }
            if (address != null) {
                PostReceipt postReceipt = new PostReceipt(address);
                cost += postReceipt.getCost();
                msg += postReceipt.send();
            }

            msg += "The total cost is: " + cost + "\n";
            msg += "------------------------------------------";
        } else {
            msg += "Unfortunately there are no tickets left :(";
        }
        System.out.println(msg);
        return msg;
    }

}
