package tech.ftchekure.documentslostandfound.service.items;

import lombok.val;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import tech.ftchekure.documentslostandfound.entities.items.FoundItem;
import tech.ftchekure.documentslostandfound.entities.items.LostItem;

import java.util.Collection;

@Component
public class FoundItemNotifierImpl implements FoundItemNotifier {

    private final JavaMailSender javaMailSender;

    public FoundItemNotifierImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void notifyFoundItem(Collection<LostItem> lostItems, FoundItem foundItem) {

        lostItems.forEach(lostItem -> sendEmail(lostItem, foundItem));

    }

    private void sendEmail(LostItem lostItem, FoundItem foundItem) {

        val to = lostItem.getEmail();
        val subject = "Potential match for your Lost Item";
        val body = new StringBuilder("We are happy to inform you that we might have found your lost item with the following details: \n");
        foundItem.getItem().getAttributeValues()
                .forEach((key, value) -> body.append(key.getName()).append(" : ").append(value).append("\n"));
        body.append("\n").append("Please log on to our site and search using the above values for more details. \n");
        body.append("www.lostdocrepo.co.zw\n");
        body.append("\n").append("If this item does not belong to you please ignore this email. \n");

        // body.append("\n Station where : ").append(foundItem.getStation().getName());
        // body.append("\nStation Address : ").append(foundItem.getStation().getAddress());
        // body.append("\nStation Location : ").append(foundItem.getStation().getLocation());

        val from = "no-reply@lostandfound.com";

        val mimeMessage = new SimpleMailMessage();
        mimeMessage.setTo(to);
        mimeMessage.setFrom(from);
        mimeMessage.setText(body.toString());
        mimeMessage.setSubject(subject);

        javaMailSender.send(mimeMessage);

    }
}
