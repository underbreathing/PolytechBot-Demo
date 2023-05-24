package com.projects.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class PrevMessage {
    public PrevMessage(){
        prevPhoto_ = null;
        prevMarkup_ = null;
        prevText_ = null;
    }
    public PrevMessage(InlineKeyboardMarkup prevMarkup, String prevText){
        prevMarkup_ = prevMarkup;
        prevText_ = prevText;
        prevPhoto_ = null;
    }
    public PrevMessage(String prevPhoto, InlineKeyboardMarkup prevMarkup){
        prevPhoto_ = prevPhoto;
        prevMarkup_ = prevMarkup;
        prevText_ = null;
    }
    public PrevMessage(String prevPhoto, InlineKeyboardMarkup prevMarkup, String prevText){
        prevPhoto_ = prevPhoto;
        prevMarkup_ = prevMarkup;
        prevText_ = prevText;
    }
    public InlineKeyboardMarkup prevMarkup_;
    public String prevText_;
    public String prevPhoto_;
}
