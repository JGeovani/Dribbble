package com.pogamadores.concrete.unit;

import android.net.Uri;
import android.test.ApplicationTestCase;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.pogamadores.concrete.application.ConcreteApplication;
import com.pogamadores.concrete.domain.Player;
import com.pogamadores.concrete.domain.Shot;
import com.pogamadores.concrete.helpers.TestConstants;
import com.pogamadores.concrete.request.GsonRequest;
import com.pogamadores.concrete.util.Constants;

import junit.framework.Assert;

import java.util.concurrent.CountDownLatch;

public class ApplicationTest extends ApplicationTestCase<ConcreteApplication> {

    private Shot validResponse = null;

    public ApplicationTest() {
        super(ConcreteApplication.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createApplication();
    }

    public void testShortParse() {
        Shot post = new Gson().fromJson(TestConstants.validShotJson, Shot.class);

        Assert.assertEquals(post.getId(),1757954);
        Assert.assertEquals(post.getTitle(),"End of an era");
        Assert.assertEquals(post.getDescription(), "<p>After 5 years, I decided to retire the old \"Contact Card\" website. My HTML and CSS is rusty, but I managed to hack this page together and make it semi-responsive. Even includes @3x assets for those on an iPhone 6 Plus!</p>\n\n<p>http://timvandamme.com</p>");
        Assert.assertEquals(post.getHeight(),600);
        Assert.assertEquals(post.getWidth(),800);
        Assert.assertEquals(post.getLikesCount(), 413);
        Assert.assertEquals(post.getCommentsCount(), 46);
        Assert.assertEquals(post.getReboundsCount(), 0);
        Assert.assertEquals(post.getUrl().toString(), "http://dribbble.com/shots/1757954-End-of-an-era");
        Assert.assertEquals(post.getShortUrl().toString(), "http://drbl.in/mAgQ");
        Assert.assertEquals(post.getViewsCount(), 12425);
        Assert.assertEquals(post.getReboundSourceId(), null);
        Assert.assertEquals(post.getImageUrl().toString(), "https://d13yacurqjgara.cloudfront.net/users/22/screenshots/1757954/tvd.png");
        Assert.assertEquals(post.getTeaserImageUrl().toString(), "https://d13yacurqjgara.cloudfront.net/users/22/screenshots/1757954/tvd_teaser.png");
        Assert.assertEquals(post.getErrorImageUrl().toString(), "https://d13yacurqjgara.cloudfront.net/users/22/screenshots/1757954/tvd_1x.png");
        Assert.assertEquals(post.getCreationDate(), "2014/10/08 18:44:19 -0400");

        Player player = post.getPlayer();

        Assert.assertNotNull(player);
        Assert.assertEquals(player.getId(),22);
        Assert.assertEquals(player.getName(), "Tim Van Damme");
        Assert.assertEquals(player.getLocation(), "San Francisco, CA");
        Assert.assertEquals(player.getFollowersCount(), 31500);
        Assert.assertEquals(player.getDrafteesCount(), 30);
        Assert.assertEquals(player.getLikesCount(), 6398);
        Assert.assertEquals(player.getLikesReceivedCount(), 24583);
        Assert.assertEquals(player.getCommentsCount(), 574);
        Assert.assertEquals(player.getCommentsReceivedCount(), 2541);
        Assert.assertEquals(player.getReboundsCount(), 7);
        Assert.assertEquals(player.getReboundsReceivedCount(), 30);
        Assert.assertEquals(player.getUrl().toString(), "http://dribbble.com/maxvoltar");
        Assert.assertEquals(player.getAvatarUrl().toString(), "https://d13yacurqjgara.cloudfront.net/users/22/avatars/normal/2dfb3c406c547c6cab0b8feb19121941.jpg?1405633606");
        Assert.assertEquals(player.getUsername(), "maxvoltar");
        Assert.assertEquals(player.getTwitterScreenName(), "maxvoltar");
        Assert.assertEquals(player.getWebsiteUrl().toString(), "http://maxvoltar.com/");
        Assert.assertEquals(player.getDraftedByPlayerId(), 1);
        Assert.assertEquals(player.getShotsCount(), 108);
        Assert.assertEquals(player.getFollowingCount(), 474);
        Assert.assertEquals(player.getCreationDate(), "2009/08/10 04:38:51 -0400");

    }

    public void testRequestShort() {

        final int shortId = 1757954;

        validResponse = null;

        Uri.Builder builder = Uri.parse(Constants.DRIBBBLE_API_URL).buildUpon();
        builder.appendPath(Constants.SHOT_PATH);
        builder.appendPath(String.valueOf(shortId));

        final CountDownLatch latch = new CountDownLatch(1);

        GsonRequest request = new GsonRequest<>(
                Request.Method.GET,
                GsonRequest.getDefaultHeaders(),
                builder.toString(),
                null,
                new Response.Listener<Shot>() {
                    @Override
                    public void onResponse(Shot response) {
                        if(response.getId() == shortId) {
                            validResponse = response;
                        }
                        latch.countDown();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        latch.countDown();
                    }
                },
                Shot.class
        );

        getApplication().addRequestToQueue(request, ApplicationTest.class.getSimpleName());
        try {
            latch.await();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Assert.assertNotNull(validResponse);
        Assert.assertEquals(validResponse.getId(),1757954);
        Assert.assertEquals(validResponse.getTitle(),"End of an era");
    }
}