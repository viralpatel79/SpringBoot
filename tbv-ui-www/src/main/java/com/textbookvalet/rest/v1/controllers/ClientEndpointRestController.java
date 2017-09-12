package com.textbookvalet.rest.v1.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientEndpoints")
public class ClientEndpointRestController {
    private final Logger logger = LoggerFactory.getLogger(ClientEndpointRestController.class);

    //@ApiOperation(value = "A list of all of the client endpoints")
    @GetMapping("/getApplicationList")
    @ResponseBody
    public Map<String, String> getApplicationList(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        if(null != clientIp && 0 == clientIp.compareTo("127.0.0.1")) {
            clientIp = request.getHeader("X-Forwarded-For");
        }
        logger.info("getApplicationList called by " + clientIp);
        Map<String, String> rc = new HashMap<>();
        rc.put("PostAuthenticateFacebook", "https://textbookvalet.com/api/v1/auth/facebook");
        rc.put("PostAccountRegister", "https://textbookvalet.com/api/v1/account/register");
        rc.put("GetAccountProfile", "https://textbookvalet.com/api/v1/account/profile");
        rc.put("GetRepresentativeApplications", "https://textbookvalet.com/api/v1/applications");
        rc.put("PostRepresentativeApplicationState", "https://textbookvalet.com/api/v1/applications/change_state");
        rc.put("PostRepresentativeApplication", "https://textbookvalet.com/api/v1/applications");
        return rc;
    }

    @GetMapping("/getUserList")
    @ResponseBody
    public Map<String, String> getUserList(
              HttpServletRequest request
            , HttpServletResponse response
            , @RequestHeader(value = "Authorization", required = true) String authorization) {
        Map<String, String> rc = getApplicationList(request);
        String clientIp = request.getRemoteAddr();
        if(null != clientIp && 0 == clientIp.compareTo("127.0.0.1")) {
            clientIp = request.getHeader("X-Forwarded-For");
        }
        if(null != authorization) {
            logger.info("getUserList with AUTHORIZATION of " + authorization + " called by " + clientIp);
            //The thought is that these APIS will be potentially be able to be varied by user
            rc.put("GetSchools", "https://textbookvalet.com/api/v1/schools");
            rc.put("PutAccountProfile", "https://textbookvalet.com/api/v1/account/profile");
            rc.put("PostAccountProfilePicture", "https://textbookvalet.com/api/v1/account/profile_picture");
            rc.put("GetUserReferralData", "https://textbookvalet.com/api/v1/users/{userId}/referral_data");
            rc.put("PostBookForBuybackPrice", "https://buy.textbookvalet.com/Buyback/rest/purchase/getBuyBackPrice");
            rc.put("GetBadgeInformation", "https://buy.textbookvalet.com/Buyback/rest/dashboard/getBadges");
            //I guess we just assume that the client will know that this call accepts a field of {category} to replace
            //I don't think the goal here is to make this self-descriptive, we have swagger for that, we are just wanting
            //the option to alter a client's endpoint
            rc.put("GetRankingInformation", "https://buy.textbookvalet.com/Buyback/rest/dashboard/getRankings/{category}");
            rc.put("GetNetworkPrizes", "https://buy.textbookvalet.com/Buyback/rest/dashboard/getNetworkPrizes");
            rc.put("GetNetworkCompetitionData", "https://buy.textbookvalet.com/Buyback/rest/dashboard/getNetworkCompetitionData");
            rc.put("PostAddItemToCart", "https://buy.textbookvalet.com/Buyback/rest/cart/addToCart");
            rc.put("PostRemoveItemFromCart", "https://buy.textbookvalet.com/Buyback/rest/cart/removeCartItem");
            rc.put("PostSaveCartTransaction", "https://buy.textbookvalet.com/Buyback/rest/purchase/saveTransaction");
            rc.put("GetCartItems", "https://buy.textbookvalet.com/Buyback/rest/cart/viewCart");
            rc.put("GetRepresentativeCalendar", "https://buy.textbookvalet.com/Scheduling/calendar/rep");
            rc.put("GetUserSummary", "https://buy.textbookvalet.com/Buyback/rest/dashboard/getUserSummary");
            rc.put("PostCalendarById", "https://buy.textbookvalet.com/Scheduling/calendar/{calendarId}");
            rc.put("GetBuybackDropoffInformation", "https://buy.textbookvalet.com/Buyback-Checkin/getInfoDropoffUser");
            rc.put("GetRepresentativeSchedulingView", "https://buy.textbookvalet.com/Scheduling/calendar/repSchedulingView");
            rc.put("GetClaimBooks", "https://buy.textbookvalet.com/Buyback-Checkin/claimBooks");
            rc.put("GetPromotionData", "https://buy.textbookvalet.com/Buyback/rest/dashboard/getPromotions");
            rc.put("PostAppointmentConfirmation", "https://buy.textbookvalet.com/Scheduling/appointments/{representativeEmail}/{appointmentId}/confirm");
        } else {
            logger.info("getUserList with NO AUTHORIZATION called by " + clientIp);
            response.setStatus(401);
        }
        return rc;
    }

}
