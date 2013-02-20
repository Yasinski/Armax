package com.imhos.security.server.social;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinskiy@gmail.com">Arthur Kasinskiy</a>
 * @updated 20.02.13 0:39
 */
public class SocialConnectController extends ConnectController {
    /**
     * Constructs a ConnectController.
     *
     * @param connectionFactoryLocator the locator for {@link org.springframework.social.connect.ConnectionFactory} instances needed to establish connections
     * @param connectionRepository     the current user's {@link org.springframework.social.connect.ConnectionRepository} needed to persist connections; must be a proxy to a request-scoped bean
     */
    public SocialConnectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, connectionRepository);
    }


    @Override
    @RequestMapping(value = "/{providerId}", method = RequestMethod.POST)
    public String connectionStatus(@PathVariable String providerId, NativeWebRequest request, Model model) {
        return super.connectionStatus(providerId, request, model);
    }

    @Override
    @RequestMapping(value = "/{providerId}", method = RequestMethod.GET)
    public RedirectView connect(@PathVariable String providerId, NativeWebRequest request) {
        return super.connect(providerId, request);
    }

    protected RedirectView connectionStatusRedirect(String providerId, NativeWebRequest request) {
        return new RedirectView("/application", true);
    }

}
