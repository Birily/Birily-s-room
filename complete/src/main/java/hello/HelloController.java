package hello;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloController {

    private Facebook facebook;
    private Twitter twitter;
    private ConnectionRepository connectionRepository;

    public HelloController(Facebook facebook, Twitter twitter, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        } else{ model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
            PagedList<Post> feed = facebook.feedOperations().getFeed();
            model.addAttribute("feed", feed);
        }
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        } else {
            model.addAttribute(twitter.userOperations().getUserProfile());
            CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
            model.addAttribute("friends", friends);
        }
        return "hello";
    }

}
