package com.aa.module.token;

import com.aa.module.miss.MissDto;

public class TokenDto  extends MissDto{
		private String uSeq;
		private String token;
		public String getuSeq() {
			return uSeq;
		}
		public void setuSeq(String uSeq) {
			this.uSeq = uSeq;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		
		
}
