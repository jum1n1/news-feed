package com.sparta.newsfeed.Feed.controller;

import com.sparta.newsfeed.Feed.dto.FeedResponseDto;
import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.Feed.service.FeedService;
import com.sparta.newsfeed.Folder.dto.FolderResponseDto;
import com.sparta.newsfeed.Folder.service.FolderService;
import com.sparta.newsfeed.User.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final FeedService feedService;
    private final FolderService folderService;

    @GetMapping("/")
    public String getFeeds(Model model) {
        List<FeedResponseDto> feeds = feedService.getFeeds();
        model.addAttribute("feeds",feeds);
        return "index";
    }

    // feedlist, folderlist page
    @GetMapping("newsfeed/feeds/user={user_id}")
    public String getFeedsByUser(@PathVariable Long user_id, Model model) {
        List<FeedResponseDto> feeds = feedService.getFeedsByUser(user_id);
        model.addAttribute("feeds", feeds);
        List<FolderResponseDto> folders = folderService.getFolders(user_id);
        model.addAttribute("folders",folders);
        return "feedList";
    }

    //feed create, update page
    @GetMapping("newsfeed/feed")
    public String getFeed(@RequestParam(required = false) Long feed_id, Model model) {
        if(feed_id==null) {
            model.addAttribute("feed", new FeedResponseDto());
        }else{
            FeedResponseDto feed = feedService.getFeed(feed_id);
            model.addAttribute("feed", feed);
        }
        return "feed";
    }

    // Feedlist in folder
    @GetMapping("newsfeed/feeds/folders={folder_id}")
    public String getFeedsByFolder(@PathVariable Long folder_id, Model model) {
        List<FeedResponseDto> feeds = feedService.getFeedsByFolder(folder_id);
        model.addAttribute("feeds",feeds);
        return "folderList";

    }
}
