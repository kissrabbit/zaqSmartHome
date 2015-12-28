package com.zaq.smartHome.pi4j.rf;


/**
 * 433M传输协议
 * @author zaqzaq
 * 2015年12月28日
 *
 */
public enum RF433Protocol {
	ONE,TWO,THREE;//后面如有扩展需要可存放于数据库中
	
	public static RF433Protocol getById(int id){
		RF433Protocol protocol=null;
		switch (id) {
			case 1:
				protocol=ONE;
				break;
			case 2:
				protocol=TWO;
				break;
			case 3:
				protocol=THREE;
				break;
		}
		return protocol;
	}
	
	private RF433Protocol() {
		switch (this.ordinal()+1) {
			case 1:
				nPulseLength=350;
				
				sendSynHigh=1;
				sendSynLow=31;
				
				send0High=1;
				send0Low=3;	
				
				send1High=3;
				send1Low=1;
				
				sendF=false;
				
				break;
			case 2:
				nPulseLength=650;
				
				sendSynHigh=1;
				sendSynLow=10;
				
				send0High=1;
				send0Low=2;	
				
				send1High=2;
				send1Low=1;
				
				sendF=false;
				
				break;
			case 3:
				nPulseLength=100;
				
				sendSynHigh=1;
				sendSynLow=71;
				
				send0High=4;
				send0Low=11;	
				
				send1High=9;
				send1Low=6;
				
				sendF=false;
				
				break;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(ONE.getnPulseLength());
		System.out.println(TWO.getnPulseLength());
		System.out.println(THREE.getnPulseLength());
		
		System.out.println(getById(2).getnPulseLength());
	}
	
	/**
	 * 脉冲震荡周期
	 */
	private int nPulseLength;
	
	/**
	 * 发送同步码 低脉冲数 
	 */
	private int sendSynLow; 
	/**
	 * 发送同步码 高脉冲数
	 */
	private int sendSynHigh;
	
	/**
	 * 发送0 低脉冲数 
	 */
	private int send0Low;
	/**
	 * 发送0 高脉冲数 
	 */
	private int send0High;
	
	/**
	 * 发送1 低脉冲数
	 */
	private int send1Low;
	/**
	 * 发送1 高脉冲数 
	 */
	private int send1High;
	
	/**
	 * 发送F 低脉冲数 
	 */
	private int sendFLow;
	/**
	 * 发送F 高脉冲数 
	 */
	private int sendFHigh;
	
	/**
	 * 是否发送F的脉冲  是则 0或1 的脉冲要发射两次 F脉冲交替发送
	 *                  否则 0或1 的脉冲只发射一次 不存在F脉冲
	 */
	private boolean sendF;

	public int getnPulseLength() {
		return nPulseLength;
	}

	public int getSendSynLow() {
		return sendSynLow;
	}

	public int getSendSynHigh() {
		return sendSynHigh;
	}

	public int getSend0Low() {
		return send0Low;
	}

	public int getSend0High() {
		return send0High;
	}

	public int getSend1Low() {
		return send1Low;
	}

	public int getSend1High() {
		return send1High;
	}

	public int getSendFLow() {
		return sendFLow;
	}

	public int getSendFHigh() {
		return sendFHigh;
	}

	public boolean isSendF() {
		return sendF;
	}
}
