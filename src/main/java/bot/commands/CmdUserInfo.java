package bot.commands;

public class CmdUserInfo //extends Command
{

//	public CmdUserInfo() {
//		name = "WhatAboutMe?!";
//		help = "displays information about the user";
//	}
//
//	@Override
//	protected void execute(CommandEvent event) {
//		EmbedBuilder build = new EmbedBuilder();
//		Member member = event.getMember();
//		User user = member.getUser();
//
//
//		build.addField("Username", user.getName().toString(), true);
//		build.addField("Nickname", member.getNickname().toString(), true);
//		build.addField("User ID", user.getId().toString(), true);
//		build.addField("Highest Role", this.getHighestRole(member), true);
//		build.setImage(user.getEffectiveAvatarUrl());
//
//		event.getChannel().sendMessage(build.build()).submit();
//	}
//
//	public String getHighestRole(Member member) {
//
//		String role;
//
//		if(member.getRoles().size() != 0) {
//			role = member.getRoles().get(0).toString();
//			}else{
//				role = "user has no roles";
//				}
//
//		return role;
//	}

}
