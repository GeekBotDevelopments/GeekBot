package bot.modules.commands;

import bot.modules.discord.Command;
import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.discordjson.json.UserData;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

public class CmdUserInfo extends Command
{
	public CmdUserInfo() {
	    super("WhatAboutMe?!");
	}

    @Override
    public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> strings)
    {
        return channel.createEmbed(embed -> {
            final Member member = message.getAuthorAsMember().block();
            final UserData userData = message.getUserData();

            embed.addField("Username", member.getDisplayName(), true);
            embed.addField("Nickname", member.getNickname().get(), true);
            embed.addField("User ID", userData.id(), true);
            embed.addField("Highest Role", this.getHighestRole(member), true);
            embed.setImage(member.getAvatarUrl());
        });
    }

	public String getHighestRole(Member member) {
		return member.getRoles().map(Role::getName).toStream().collect(Collectors.joining(", "));
	}
}
