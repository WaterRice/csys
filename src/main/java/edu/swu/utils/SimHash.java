/**  

* 创建时间：2018年12月9日 下午3:34:11  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：SimHash.java  

* 类说明：  

*/

package edu.swu.utils;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.Iterator;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

public class SimHash {

	private String tokens;

	private BigInteger intSimHash;

	private int hashbits = 64;
	
	public BigInteger getIntSimHash() {
		return this.intSimHash;
	}

	public SimHash(String tokens) throws IOException {
		this.tokens = tokens;
		this.intSimHash = this.simHash();
	}

	public SimHash(String tokens, int hashbits) throws IOException {
		this.tokens = tokens;
		this.hashbits = hashbits;
		this.intSimHash = this.simHash();
	}

	public BigInteger simHash() throws IOException {
		int[] v = new int[this.hashbits];

		// StringTokenizer stringTokens = new StringTokenizer(this.tokens);

		//加入明显的停用词
		String[] self_stop_words = { "的", "在", "了", "呢", "，", "0", "：", ",", "是","h","<",">","/" };
		CharArraySet cas = new CharArraySet(Version.LUCENE_40, 0, true);
		for (int i = 0; i < self_stop_words.length; i++) {
			cas.add(self_stop_words[i]);
		}

		// 加入系统默认停用词
		Iterator<Object> itor = SmartChineseAnalyzer.getDefaultStopSet().iterator();
		while (itor.hasNext()) {
			cas.add(itor.next());
		}

		// 中英文混合分词器(其他几个分词器对中文的分析都不行)
		@SuppressWarnings("resource")
		SmartChineseAnalyzer sca = new SmartChineseAnalyzer(Version.LUCENE_40, cas);

		StringReader reader = new StringReader(this.tokens);
		TokenStream ts = sca.tokenStream("field", reader);
		CharTermAttribute ch = ts.addAttribute(CharTermAttribute.class);

//        while (stringTokens.hasMoreTokens()) {  
//            String temp = stringTokens.nextToken();  
//            BigInteger t = this.hash(temp);  
//            for (int i = 0; i < this.hashbits; i++) {  
//                BigInteger bitmask = new BigInteger("1").shiftLeft(i);  
//                 if (t.and(bitmask).signum() != 0) {  
//                    v[i] += 1;  
//                } else {  
//                    v[i] -= 1;  
//                }  
//            }  
//        }
		ts.reset();
		while (ts.incrementToken()) {
			String tmp = ch.toString();
			BigInteger t = this.hash(tmp);
			for (int i = 0; i < this.hashbits; i++) {
				BigInteger bitmask = new BigInteger("1").shiftLeft(i);
				if (t.and(bitmask).signum() != 0) {
					v[i] += 1;
				} else {
					v[i] -= 1;
				}
			}
		}

		BigInteger fingerprint = new BigInteger("0");
		StringBuffer simHashBuffer = new StringBuffer();
		for (int i = 0; i < this.hashbits; i++) {
			if (v[i] >= 0) {
				fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
				simHashBuffer.append("1");
			} else {
				simHashBuffer.append("0");
			}
		}
		//this.strSimHash = simHashBuffer.toString();
		//System.out.println(this.strSimHash + " length " + this.strSimHash.length());
		return fingerprint;
	}

	private BigInteger hash(String source) {
		if (source == null || source.length() == 0) {
			return new BigInteger("0");
		} else {
			char[] sourceArray = source.toCharArray();
			BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
			BigInteger m = new BigInteger("10000000000003");
			BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(new BigInteger("1"));
			for (char item : sourceArray) {
				BigInteger temp = BigInteger.valueOf((long) item);
				x = x.multiply(m).xor(temp).and(mask);
			}
			x = x.xor(new BigInteger(String.valueOf(source.length())));
			if (x.equals(new BigInteger("-1"))) {
				x = new BigInteger("-2");
			}
			return x;
		}
	}

	/**
	 * 取两个二进制的异或，统计为1的个数，就是海明距离
	 * 
	 * @param other
	 * @return
	 */

	public int hammingDistance(SimHash other) {

		BigInteger x = this.intSimHash.xor(other.intSimHash);
		int tot = 0;

		// 统计x中二进制位数为1的个数
		// 我们想想，一个二进制数减去1，那么，从最后那个1（包括那个1）后面的数字全都反了，对吧，然后，n&(n-1)就相当于把后面的数字清0，
		// 我们看n能做多少次这样的操作就OK了。

		while (x.signum() != 0) {
			tot += 1;
			x = x.and(x.subtract(new BigInteger("1")));
		}
		return tot;
	}

//	public static void main(String[] args) throws IOException {
//		
//		String str1 = "夕阳老去，西风渐紧。    叶落了，秋就乘着落叶来了。秋来了，人就随着秋瘦了，随着秋愁了 。 但金黄的落叶没有哀愁，它懂得如何在秋风中安慰自己，它知道，自己的沉睡是为了新的醒来。    落叶有落叶的好处，可以不再陷入爱情的纠葛了；落叶有落叶的美，它是疲倦了的蝴蝶。我甚至能感觉到落下来的叶子们轻轻的叫喊。    那一刻，我的心微微一颤，仿佛众多下落的叶子中的一枚。    我看到了故乡，看到了老家门前那棵生生不息的老树，看到了炊烟因为游子的归来而晃动。对于远走他乡的脚，对于飞上天空的翅膀，炊烟是永不扯断的绳子。就像路 口的大树，它的枝干指着许多的路，而起点只有一个，终点也只有一个，每个离开村庄的人，都带走了   一片绿叶，却留下一条根。    我看到了故乡的山崖，看到石头在山崖上，和花朵一起争着绽放；看到羊在山崖上，和云一起争着飘荡。    我看到了我的屋檐，冬天时结满冰凌，夏天时蓄满鸟鸣，一串红辣椒常常被看作是穷日子里的火种，守着屋檐上下翻飞的麻雀，总是那么和谐地与庄户人家好好地过日子。时时刻刻缠绕着那颗在路上的心的，就是这个屋檐。    我看到了母亲，为了不让我们在冬天里挨冻，她拾起一节节枯枝，犹如把那些破碎的日子一一点缀，然后，把温暖交到我们手上。柴垛越码越高，母亲却越来越矮。我 看到母亲那干瘪的乳房，像两只残缺不整的讨饭的碗，却为我们讨来了一生的盛宴。母亲在灶坑里点燃的红色的昏暗的火焰，成了那些夜里我们唯一可以依靠的肩膀，唯一可以握住的暖暖的手。    叶落归根，是我老了吗？我们花了很多时间去争取财富，却很少有时间去享受；我们越来越大的房子，但却越来越少地住在家里；到月球然后回来，却发现到楼下邻居家都很困难；征服了外面的世界，对自己的内心世界却一无所知。    远行的人，是什么声音使你隐姓埋名？是什么风将你吹往他乡？秋天就是这样，把叶子纷纷抖落，把人的思念纷纷挂上枝头。该回去了，去看看那棵生下我、让我因成长而绿又让我因成熟而黄的大树，还有落叶里沉睡的母亲。母亲，我匆匆的脚步就是你密密缝合的针脚。母亲，背着破烂行李的我要归来，找到了天堂的我也要归来。    一层层落叶铺在回家的路上，我要踩着温暖的地毯去看望母亲。母亲也像落叶，从灿烂的枝头缓缓地落下来，只是，她没有再醒来……    这个世界，能留住人的不是房屋，能带走人的不是道路。岁月无法伸出一只手，替你抓住过往的云。如果一切还能重新拾捡回来，母亲，我要去拾取你的笑容、脚步和风，用你的爱做灯油，用你的善良做捻儿，我要点燃它，放在心里，一辈子不忘回家的路。    天冷了，树的叶子落下来，树离我很近。我似乎听见了它们在缓缓凝固。    天冷了，它们一排一排地站着，心中坚守着秘密一阵阵地疼痛起来。但叶子落下来，掩盖了一切。    母亲去了，心灵没有了依靠，一下子就有了那种到处漏风的感觉。可是大风一直在刮，把故乡周围的尘土刮了个干净。我小小的故乡正在被秋天所包裹。    母亲的坟上有一棵树，那是我写给母亲的诗。每到秋天，叶子纷纷落下，把母亲的坟头遮盖得严严实实。那些在风中微微呻吟着的落叶，远远望去，像一群疲倦了的蝴蝶，静静地收拢着它们一生的美丽瞬间：一朵红晕，一个誓言，或者是简单的一声叹息。";
//		String str2 = "前些天看的起风了，今天点到了贴吧来看看，1点半关了电脑，然后脑子里面一直是起风了的想法和延伸，3点又爬起来开了电脑。写这篇文章不是想评判什么，仅是说些自己内心所想的。大家基本在吐槽起风了的剧情如何的不合理，首先先从剧情开始说起吧，起风了我看的也是盗版的，因为没有正版可看。本来看盗版就不应该再说什么了，但是还是写些东西为好，来纪念这部作品。宫崎骏大师的作品擅长构造世界，作为全球2大动画电影的代表，宫崎骏和迪斯尼分庭抗礼，迪斯尼深扎根欧美文化，受个人英雄主义的色彩影响，所以迪斯尼描写现实很真实细腻，个人力量微小但是团体的力量可以改变世界，可以这么说迪斯尼所代表的西方文化讲的是觉醒。宫崎骏的动画其实探讨的是如何的运用力量，当一个强大的人拥有足可以改变世界的力量的时候，面对力量和个人以及世界的平衡。当然这段话也许过于绝对，姑且如此来看。起风了看完的时候哭了。。我记得我上次哭是去年的国庆，依然是看着盗版的宫崎骏大师的萤火虫之墓。起风了看完的感觉就是哭瞎了。。。别无在多的情绪有一个人在你美丽的时候和你相遇是幸福的，但是在最美好的时候和你离开是痛苦的。二郎和女主的爱情故事很平淡，平淡的简直可以被白菜韩剧和天朝神剧玩爆太平洋的距离，但就是这样，我还是差点哭瞎，我一直告诉自己这是影像而已，但是我还是被感动了。有人会举里面二郎天天为了飞机，就那个事业和梦想以及在女主身旁抽烟，他真的有爱过她么？我告诉你周总理为了国家也很少陪邓颖超！至于抽烟？你看看你老公抽不抽。再去回看电影！看评论大部分人都在说一个所谓的战争问题和道德问题，我给大家拆开大环境看吧！为什么安倍政权会重新上台，要知道这货是被踹下去的。日本整体大环境是讨厌战争没有错，但是日本人其实尤其是青年人更讨厌中韩在过去的问题上喋喋不休的纠缠过去，日本人讨厌反思拒绝承认错误的根源也在于此，这是我爷爷辈干的事，你天天还要算到我头上来？所谓安倍的强大日本在日本很有市场，没有一个人不希望自己的国家是强大的，这也是片子二郎和他朋友去德国学习制造飞机，希望用自己的努力去改变落后的国家现状的动力，错的不是他们，而是政府。今日大国为什么要打仗，最重要的理由就是利己主义，而不是浪漫主义。如果制造武器被人拿去战争那就是道德问题，那所有的人都得上道德的烤刑架。这不现实。至于参拜靖国神社，其实这个问题很好理解，就和中国人去祭奠英雄纪念碑一样。不过我不知道有多少人去祭奠过，对此呵呵！宫崎骏大师讨厌战争，日本人也讨厌战争，我天朝的神剧经常里面有日本人大义灭亲的，但是更多的日本人为了所谓的国家利益在战场上拼杀的时候，我们自己在做着什么呢？打内战？我不知道！！！二郎明知道自己的飞机是用去战争，为什么还要制造？单纯因为是为了梦想么？不！哪怕这个国家做的是做的，我能做的就是尽可能的用自己的努力减少国家的损失，最起码在一段时间内零式确实帮助日本海军得到了制空权。 起风了也许很散，但是从来都形不散，面对国家梦想爱人，你该去如何抉择？我想我最起码做不到二郎那么高尚！毕竟邓小平主席的孙子和孙女都入的是美帝的国籍。片中二郎给出了自己的选择，我想这也许是宫崎骏大师和无数日本人的选择。如果我的国家选择的是错的，我能做的仅是燃尽自己，让这个国家少受些伤害。不是一走了之！对错仅是立场而已。回过头去看哈尔的移动城堡，哈尔是个强大的魔法师，希望依靠一己之力来阻止战争，但是战争是怎么发生的呢？因为邻国的王子被变成了稻草人失踪了。。。风之谷中战争是报复性或者说是就是绝对的利己性质的，至于幽灵公主则是贪婪憎恨，每个人都拥有强大的力量，选择其实不在乎对错，而是立场就这么简单！我可以理解，但是并不代表我会认同。最起码起风了，很坦然！宋朝之后无中国,明朝之后无华夏！我不知道有几个人看过这段话。当大清的瓜瓢为了削发换服大肆屠杀汉人的时候以及百年屈辱，也许那群瓜瓢自己都没想到，百年之后这些事迹竟然养活了大批的电视电影从业者。并且被美化了所谓的历史！  我曾经问过我的导师，如果抗日战争失败了，中国历史基本和大清史区别不大！如果有一天中国征服了日本，那么当年抗日战争的英雄面临的就是和岳飞一样的尴尬境地，妨碍民族融合。中国史最奇葩的一点就是，别人进来XXOO了以后，就变成干爹了！起风了看的其实不是剧情，有人说它的画面和音乐怎么怎么样，如果你看到这个不妨去看冰雪奇缘。迪斯尼的诚意之作。为了设计好冰雪魔法迪斯尼煞费苦心，具体的你可以百度。做为宫大师的收山之作，个人给予的评价是这是最好的作品，同时也是最坏的！不过是因为以后再也看不到宫大师的作品了而已。不少的人都看过史泰龙的第一滴血。那电影直接拍了4部！其实那电影没多大意思。但是第一滴血这个电影系列，最令人尊敬之处在于：它决然不同于而今流行的某些给政府搭台帮腔的所谓美国主旋律作品！而是始终持有一种可贵的批判态度，而这种独立的精神和不合作的态度，正是美国电影能够始终保持新鲜和活力的根本所在，电影涉及政治但不服务于政治，是电影作为一门艺术形式的基本准则！起风了不是从剧情上看的。他要求的是一个抛开立场和偏见的代入。所以我并没有太多的去谈论剧情！还记得兰博的那句台词么？我希望这个国家可以像我爱他一样爱我！李克强总理说中国人均月收入8000.这基本是我大半年的收入了。对此我表示我拖了国家的后退！深表惭愧！ 写到这里不妨回看些东西！中日钓鱼岛岛争！首先明确一个事情！钓鱼岛的实际控制权是在日本手里，央视的新闻也自己承认。其次中日矛盾到现在一直无法平息的一个问题就是，中国政府改变以往的看见不说的态度，主动大胆的说出了这个问题。并且一直再说！喋喋不休的说！此时国内的大环境是房价飙升物价飞涨，内部动荡不堪。不信的自己回顾过去的事件。其深层的原因不过是所谓的祸水东引罢了！套用法兰克福学派在关于大众文化传播中的解释，当内部问题暂时无法调和的时候，需要一个外部的敌人来转移内部危险，日本就是那个天然的危险。不过最后的示威游行活活的是坑了自己人。国际上丢了人罢了！五毛党肯定会说，别问国家能为你做什么！问问自己，你能为国家做什么？人都会站在道德的制高点去审批别人！就如同文章事件一样！所有的人和疯了一样。马航上的100多个同胞无人问津了？而且大把的蠢货跑去姚晨的微博骂人家去了，简直神奇。。。  如果你无法高尚。那你就别去嘲讽他人的卑劣。如果你本身就很卑劣，那请不要嘲笑他人的高尚。最起码当我们看着盗版的起风了的时候，留些口德！宫崎骏的作品基本在国内看不到很重要的原因就是，看了他的作品你会思考，如果一旦思考那就出问题了。中国的电视剧很少拍民国的原因就是意识形态！中国教育的目的就是讲人变成不会思考的猪猡！可能此言过了！当韩寒的开明公民被人一脚从神坛踹下！证明他不过也是个既得利益的小人。如果你去思考，你就会发现很多问题，但是发现这些问题的时候就会很麻烦的！落后就要挨打！但是我们为什么落后？可有人想过或者有人告诉你这个问题的答案？ 电影文化音乐这些艺术也许没有国界。但是人有，我们骂日本人除了骂还能干嘛？我们和日本学的东西太多了。人家用1亿人口可以创造我们13亿人口所创造的价值，这就是我们最应该学习的，不要被偏见蒙蔽了眼睛。举个最简单的例子，我国军队军演，外国前来侦查，然后外交部就开始抗议！军队是用来打仗的。难道以后打仗你也准备喊外交部的给你抗议去么？说点题外话，我和朋友开玩笑说怎么评价中国电影观众的水平！我朋友罗里吧嗦了一大堆，我说！2012的3D仅在中国上映！足以！";		String str3 = "它是心灵中的一种空洞，是头脑中的一个理想，是理性思维中的创造潜力，是情感活动中的一股勃勃的老七横秋。年轻，意味着甘愿放弃温馨浪漫的爱情去闯荡生活，意味着超越羞涩、怯懦和欲望的胆识与气质。而60岁的男人可能比20岁的小伙子更多地拥有这种胆识与气质。没有人仅仅因为时光的流逝而变得衰老，只是随着理想的毁灭，人类才出现了老人。岁月可以在皮肤上留下皱纹，却无法为灵魂刻上一丝痕迹。年轻，并非人生旅程的一段时光，也并非粉颊红唇和体魄的矫健。忧虑、恐惧、缺乏自信才使人佝偻于时间尘埃之中。无论是60岁还是16岁，每个人都会被未来所吸引，都会对人生竞争中的欢乐怀着孩子般无穷无尽的渴望。在你我心灵的深处，同样有一个无线电台，只要它不停地从人群中，从无限的时间中接受美好、希望、欢欣、勇气和力量的信息，你我就永远年轻。一旦这无线电台坍塌，你的心便会被玩世不恭和悲观失望的寒冷酷雪所覆盖，你便衰老了—即使你只有20岁。但如果这无线电台始终矗立在你心中，捕捉着每个乐观向上的电波，你便有希望超过年轻的80岁。所以只要勇于有梦，敢于追梦，勤于圆梦，我们就永远年轻！千万不要动不动就说自己老了，错误引导自己！年轻就是力量，有梦就有未来！";
//		SimHash hash1 = new SimHash(str1);
//		SimHash hash2 = new SimHash(str2);
//		SimHash hash3 = new SimHash(str3);
//	
//		System.out.println(hash1.hammingDistance(hash2) + " " + str2.length());
//		System.out.println(hash2.hammingDistance(hash3) + " " + str3.length());
//	}
}
