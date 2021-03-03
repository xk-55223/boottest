package com.keith.test.boottest.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * 字符串工具类
 *
 * @author Luke
 * @date 2019-08-12 21:01
 */
@Slf4j
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static void main(String[] args) throws Exception {
        Map<String, String> header = new HashMap<>();
        header.put("Connection", "keep-alive");
        String result = HttpClientUtil.httpGet("https://weidian.com/item.html?itemID=4188765796"
                , header, null);
        String source = extractString(result, "data-obj=\"(.*)\"\\ssrc=", 1);
        //String source = "{&#34;status&#34;:{&#34;code&#34;:0,&#34;message&#34;:&#34;OK&#34;,&#34;description&#34;:&#34;&#34;},&#34;result&#34;:{&#34;default_model&#34;:{&#34;service_promise&#34;:{&#34;block_success&#34;:true,&#34;support_promises&#34;:[{&#34;promise_desc&#34;:&#34;确认收货前产生退货退款，退款成功后可对退货产生的单程运费提供补贴服务&#34;,&#34;promise_name&#34;:&#34;退货包运费&#34;,&#34;promise_type&#34;:&#34;finsurance&#34;},{&#34;promise_desc&#34;:&#34;该卖家已开通了交易资金担保服务，交易将由微店提供资金担保，在买家确认收货后（或自卖家确认发货之日起，买卖双方约定的自动确认收货时间到期日后）结算给卖家&#34;,&#34;promise_name&#34;:&#34;交易资金担保&#34;,&#34;promise_type&#34;:&#34;warrant&#34;},{&#34;promise_desc&#34;:&#34;该店铺是企业店铺，已通过企业微店认证&#34;,&#34;promise_name&#34;:&#34;企业店&#34;,&#34;promise_type&#34;:&#34;enterprise_shop&#34;},{&#34;promise_desc&#34;:&#34;该商家已通过证件认证，身份信息已在微店备案&#34;,&#34;promise_name&#34;:&#34;店长实名认证&#34;,&#34;promise_type&#34;:&#34;card_recognized&#34;}]},&#34;item_info&#34;:{&#34;bg_category_id&#34;:1512,&#34;block_success&#34;:true,&#34;calendarItem&#34;:false,&#34;collect_count&#34;:0,&#34;flag&#34;:{&#34;is_tax_rate&#34;:false,&#34;is_stock_limit&#34;:false,&#34;is_valid_item&#34;:true,&#34;is_point_item&#34;:false,&#34;is_cvs_item&#34;:false,&#34;is_sale_floor_item_conflict&#34;:false,&#34;is_future_sold&#34;:false,&#34;is_status_deleted&#34;:false,&#34;is_red_fxmall_fans&#34;:true,&#34;is_show_no_refund_text&#34;:false,&#34;is_status_off_shelve&#34;:false,&#34;is_shop_private&#34;:true,&#34;is_official_limit&#34;:false,&#34;is_app_only_for_wd_point&#34;:false,&#34;is_show_express_fee&#34;:true,&#34;is_app_only_for_limit_discount&#34;:false,&#34;is_cps_item&#34;:false,&#34;is_certification_invalid_item&#34;:false,&#34;is_show_test_center&#34;:false,&#34;is_quick_order&#34;:false,&#34;is_item_private_risk&#34;:false,&#34;is_hide_ad&#34;:false,&#34;is_can_cart&#34;:true,&#34;is_shopping_center&#34;:true,&#34;is_support_wd_instalment&#34;:false},&#34;imgs&#34;:[&#34;https://si.geilicdn.com/wdseller343240340-2cb800000175b75ac9c30a21348d-unadjust_800_800.jpeg&#34;,&#34;https://si.geilicdn.com/wdseller343240340-20cb00000175b75ac92e0a21c2a8-unadjust_800_800.jpeg&#34;,&#34;https://si.geilicdn.com/wdseller343240340-532b00000175b75ac9c90a217216-unadjust_800_800.jpeg&#34;],&#34;itemLowPrice&#34;:88800,&#34;itemSellable&#34;:true,&#34;itemShareDesc&#34;:&#34;【新亮点品质】小辣椒X50Pro八核8+128G全网通4G安卓智能手机学生价百元游戏&#34;,&#34;item_head&#34;:&#34;https://si.geilicdn.com/wdseller343240340-2cb800000175b75ac9c30a21348d-unadjust_800_800.jpeg&#34;,&#34;item_head_thumb&#34;:&#34;https://si.geilicdn.com/wdseller343240340-2cb800000175b75ac9c30a21348d-unadjust_800_800.jpeg?w=110&amp;h=110&amp;cp=1&#34;,&#34;item_id&#34;:&#34;4188765796&#34;,&#34;item_name&#34;:&#34;【新亮点品质】小辣椒X50Pro八核8+128G全网通4G安卓智能手机学生价百元游戏&#34;,&#34;knowledgeFreeCollection&#34;:false,&#34;origin_price&#34;:&#34;888&#34;,&#34;risk_info&#34;:{&#34;risk_code&#34;:0,&#34;risk_msg&#34;:&#34;&#34;},&#34;sellerCloseInstallment&#34;:false,&#34;shop_id&#34;:940874252,&#34;source_id&#34;:&#34;f5c9421654e92902b742e7037fd4626e&#34;,&#34;special_price_type&#34;:0,&#34;stock&#34;:594,&#34;ticketItem&#34;:false,&#34;user_item_collected&#34;:false},&#34;shop_info&#34;:{&#34;block_success&#34;:true,&#34;chain_store_can_change&#34;:false,&#34;position_info&#34;:{&#34;address&#34;:&#34;枫花圆汽车影院&#34;,&#34;address_nation&#34;:&#34;中国&#34;,&#34;lat&#34;:&#34;39.95414&#34;,&#34;lng&#34;:&#34;116.474052&#34;},&#34;qrcode_url&#34;:&#34;https://si.geilicdn.com/weidian940874252-6e7e00000171fc25a9840a21c2a8_984_984.jpg&#34;,&#34;repurchase_rate&#34;:&#34;3&#34;,&#34;seller_logo&#34;:&#34;https://si.geilicdn.com/h5user940874252-57f5000001767a079d410a20b7b9_400_400.jpg?w=160&amp;h=160&amp;cp=1&#34;,&#34;shopName&#34;:&#34;馋嘴新疆店铺\uD83D\uDE40&#34;,&#34;shopShowConfig&#34;:{&#34;hideCustomerSay&#34;:false,&#34;hideItemComment&#34;:false,&#34;hideShopAttention&#34;:false},&#34;shop_avg_delivery_time&#34;:&#34;24小时内&#34;,&#34;shop_brand_logo&#34;:[],&#34;shop_collect_count&#34;:3189,&#34;shop_credit&#34;:{&#34;credit_num&#34;:3,&#34;credit_type&#34;:2},&#34;shop_flag&#34;:[],&#34;shop_id&#34;:940874252,&#34;shop_last_login_time&#34;:&#34;30分钟前登录&#34;,&#34;shop_logo&#34;:&#34;https://si.geilicdn.com/weidian940874252-6fb00000017231f7277a0a21c2a7_984_984.jpg?w=250&amp;h=250&amp;cp=1&#34;,&#34;shop_status&#34;:0,&#34;shop_url&#34;:&#34;https://weidian.com/?userid=940874252&#34;,&#34;user_shop_collected&#34;:false},&#34;sku_properties&#34;:{&#34;block_success&#34;:true,&#34;sku&#34;:{&#34;44564647669&#34;:{&#34;id&#34;:44564647669,&#34;img&#34;:&#34;https://si.geilicdn.com/wdseller343240340-38df00000175b75c255b0a20b7b9-unadjust_800_800.jpeg&#34;,&#34;origin_price&#34;:&#34;888.00&#34;,&#34;price&#34;:&#34;888.00&#34;,&#34;stock&#34;:67,&#34;title&#34;:&#34;蓝色;4+64&#34;},&#34;44564647671&#34;:{&#34;id&#34;:44564647671,&#34;img&#34;:&#34;https://si.geilicdn.com/wdseller343240340-38df00000175b75c255b0a20b7b9-unadjust_800_800.jpeg&#34;,&#34;origin_price&#34;:&#34;888.00&#34;,&#34;price&#34;:&#34;888.00&#34;,&#34;stock&#34;:64,&#34;title&#34;:&#34;蓝色;6+64&#34;},&#34;44564647673&#34;:{&#34;id&#34;:44564647673,&#34;img&#34;:&#34;https://si.geilicdn.com/wdseller343240340-38df00000175b75c255b0a20b7b9-unadjust_800_800.jpeg&#34;,&#34;origin_price&#34;:&#34;888.00&#34;,&#34;price&#34;:&#34;888.00&#34;,&#34;stock&#34;:67,&#34;title&#34;:&#34;蓝色;8+128&#34;},&#34;44564647675&#34;:{&#34;id&#34;:44564647675,&#34;img&#34;:&#34;https://si.geilicdn.com/wdseller343240340-1d0700000175b75c35850a21c2a7-unadjust_800_800.jpeg&#34;,&#34;origin_price&#34;:&#34;888.00&#34;,&#34;price&#34;:&#34;888.00&#34;,&#34;stock&#34;:67,&#34;title&#34;:&#34;黑色;4+64&#34;},&#34;44564647677&#34;:{&#34;id&#34;:44564647677,&#34;img&#34;:&#34;https://si.geilicdn.com/wdseller343240340-1d0700000175b75c35850a21c2a7-unadjust_800_800.jpeg&#34;,&#34;origin_price&#34;:&#34;888.00&#34;,&#34;price&#34;:&#34;888.00&#34;,&#34;stock&#34;:64,&#34;title&#34;:&#34;黑色;6+64&#34;},&#34;44564685670&#34;:{&#34;id&#34;:44564685670,&#34;img&#34;:&#34;https://si.geilicdn.com/wdseller343240340-1d0700000175b75c35850a21c2a7-unadjust_800_800.jpeg&#34;,&#34;origin_price&#34;:&#34;888.00&#34;,&#34;price&#34;:&#34;888.00&#34;,&#34;stock&#34;:67,&#34;title&#34;:&#34;黑色;8+128&#34;},&#34;44564685672&#34;:{&#34;id&#34;:44564685672,&#34;img&#34;:&#34;https://si.geilicdn.com/wdseller343240340-583b00000175b75c458d0a217216-unadjust_800_800.jpeg&#34;,&#34;origin_price&#34;:&#34;888.00&#34;,&#34;price&#34;:&#34;888.00&#34;,&#34;stock&#34;:67,&#34;title&#34;:&#34;粉色;4+64&#34;},&#34;44564685674&#34;:{&#34;id&#34;:44564685674,&#34;img&#34;:&#34;https://si.geilicdn.com/wdseller343240340-583b00000175b75c458d0a217216-unadjust_800_800.jpeg&#34;,&#34;origin_price&#34;:&#34;888.00&#34;,&#34;price&#34;:&#34;888.00&#34;,&#34;stock&#34;:64,&#34;title&#34;:&#34;粉色;6+64&#34;},&#34;44564685676&#34;:{&#34;id&#34;:44564685676,&#34;img&#34;:&#34;https://si.geilicdn.com/wdseller343240340-583b00000175b75c458d0a217216-unadjust_800_800.jpeg&#34;,&#34;origin_price&#34;:&#34;888.00&#34;,&#34;price&#34;:&#34;888.00&#34;,&#34;stock&#34;:67,&#34;title&#34;:&#34;粉色;8+128&#34;}},&#34;skuCount&#34;:9,&#34;skuShowName&#34;:&#34;蓝色;4+64&#34;},&#34;page_jump&#34;:{&#34;block_success&#34;:true,&#34;need_jump&#34;:false},&#34;delivery_info&#34;:{&#34;block_success&#34;:true,&#34;chosedAddress&#34;:{&#34;addressStr&#34;:&#34;北京市-朝阳区&#34;,&#34;city&#34;:4895,&#34;province&#34;:1001},&#34;deliveryServices&#34;:[],&#34;expressFeeDesc&#34;:&#34;0.0元起&#34;,&#34;expressPostageDesc&#34;:&#34;0.0元起, 满1件包邮&#34;,&#34;postageInfos&#34;:[{&#34;deliveryAreaDes&#34;:&#34;包邮地区：安徽省，江苏省，江西省，上海市，浙江省，北京市，河北省，内蒙古自治区，山东省，山西省，天津市，河南省，湖北省，湖南省，福建省，广东省，广西壮族自治区，海南省，钓鱼岛，黑龙江省，吉林省，辽宁省，甘肃省，宁夏回族自治区，青海省，陕西省，贵州省，四川省，云南省，重庆市&#34;,&#34;deliveryDes&#34;:&#34;全店满1件包邮&#34;,&#34;deliveryTitle&#34;:&#34;满包邮&#34;,&#34;deliveryType&#34;:2,&#34;order&#34;:0}]},&#34;promotion_info&#34;:{&#34;block_success&#34;:true,&#34;couponDiscount&#34;:{&#34;bestDiscountTip&#34;:&#34;可享以下优惠&#34;,&#34;hasCashCoupon&#34;:false,&#34;jumpFloatLayer&#34;:false,&#34;promotionEntranceText&#34;:&#34;领券&#34;,&#34;unfetchedCoupons&#34;:[]},&#34;coupons&#34;:[{&#34;alreadyFetched&#34;:false,&#34;alreadyFetchedCount&#34;:0,&#34;bizType&#34;:2,&#34;buyerLimit&#34;:1,&#34;continueToFetch&#34;:false,&#34;couponId&#34;:&#34;12149257&#34;,&#34;couponUrl&#34;:&#34;https://weidian.com/?userid=940874252&#34;,&#34;endTime&#34;:1614085200000,&#34;full&#34;:10000,&#34;inflateCoupon&#34;:false,&#34;reduce&#34;:2900,&#34;shopId&#34;:940874252,&#34;startTime&#34;:1613480400000,&#34;statusType&#34;:0,&#34;text&#34;:&#34;满100减29&#34;,&#34;typeName&#34;:&#34;全店通用&#34;}],&#34;popupCoupons&#34;:{&#34;alreadyFetchedCoupons&#34;:[],&#34;unFetchedCoupons&#34;:[{&#34;alreadyFetched&#34;:false,&#34;alreadyFetchedCount&#34;:0,&#34;bizType&#34;:2,&#34;buyerLimit&#34;:1,&#34;continueToFetch&#34;:false,&#34;couponId&#34;:&#34;12149257&#34;,&#34;couponUrl&#34;:&#34;https://weidian.com/?userid=940874252&#34;,&#34;endTime&#34;:1614085200000,&#34;full&#34;:10000,&#34;inflateCoupon&#34;:false,&#34;reduce&#34;:2900,&#34;shopId&#34;:940874252,&#34;startTime&#34;:1613480400000,&#34;statusType&#34;:0,&#34;text&#34;:&#34;满100元可用&#34;,&#34;typeName&#34;:&#34;全店通用&#34;}]},&#34;promotions&#34;:[]},&#34;consumer_protect&#34;:{&#34;block_success&#34;:true},&#34;seller_rule&#34;:{&#34;block_success&#34;:true}}},&#34;preInterfaceData&#34;:{&#34;fastPayInfo&#34;:{&#34;status&#34;:{&#34;code&#34;:0,&#34;message&#34;:&#34;OK&#34;,&#34;description&#34;:&#34;&#34;},&#34;result&#34;:{&#34;buttonName&#34;:&#34;去开通&#34;,&#34;fastPayOpenUrl&#34;:&#34;https://weidian.com/pay-h5/basis/extreme-payment.html?ffr=h5_item&amp;redirectUrl=$$_REDIRECT_URL_$$#/speedUp&#34;,&#34;fastpayText&#34;:&#34;极速支付，抢购快人一步！限时领48元支付礼包，本单可用&#34;,&#34;haveCard&#34;:&#34;S&#34;,&#34;isOpen&#34;:&#34;F&#34;,&#34;isShow&#34;:&#34;S&#34;}},&#34;ABtestFlag&#34;:{&#34;status&#34;:{&#34;code&#34;:0,&#34;message&#34;:&#34;OK&#34;,&#34;description&#34;:&#34;&#34;},&#34;result&#34;:{&#34;currentTime&#34;:1614069614971,&#34;data&#34;:{&#34;h5_item_detail&#34;:{&#34;spaceSort&#34;:0,&#34;dataList&#34;:[{&#34;renderImage&#34;:null,&#34;renderDesc&#34;:&#34;触发登录弹层&#34;,&#34;redirectUrl&#34;:null,&#34;renderDataJson&#34;:{},&#34;relationId&#34;:38002,&#34;exhibitId&#34;:926,&#34;materialId&#34;:36200,&#34;crowdId&#34;:10,&#34;spaceId&#34;:4191,&#34;features&#34;:&#34;{\\&#34;openMaterialExperiment\\&#34;:true}&#34;,&#34;materialKey&#34;:&#34;2028fa1909c546f78fa3ed60425f8eb0&#34;,&#34;serverTime&#34;:1614069614971,&#34;startTime&#34;:1587538800000,&#34;endTime&#34;:1892620500000,&#34;renderType&#34;:&#34;none&#34;,&#34;spoor&#34;:&#34;1011.926.4191.36200.10&#34;,&#34;renderTitle&#34;:&#34;1&#34;,&#34;materialSortValue&#34;:0}],&#34;renderType&#34;:&#34;none&#34;,&#34;spaceCode&#34;:&#34;h5_item_detail&#34;}},&#34;totalCount&#34;:1}}},&#34;skeleton&#34;:&#34;&lt;div class=\\&#34;index-st-wrap\\&#34;&gt;\\n    \\n    \\n    \\n    &lt;div class=\\&#34;content\\&#34;&gt;\\n    \\n      &lt;div class=\\&#34;content-img\\&#34; style=\\&#34;padding-top: 0px;\\&#34;&gt;\\n      \\n        &lt;img class=\\&#34;first-img\\&#34; src=\\&#34;https://si.geilicdn.com/wdseller343240340-2cb800000175b75ac9c30a21348d-unadjust_800_800.jpeg?w=30&amp;amp;h=30&amp;amp;cp=1\\&#34; alt=\\&#34;\\&#34;/&gt;\\n        &lt;span class=\\&#34;item-imgs-tip\\&#34;&gt;\\n          \\n        &lt;/span&gt;\\n      &lt;/div&gt;\\n      &lt;!-- 限时折扣价格区域 --&gt;\\n      \\n      &lt;!-- 预售 --&gt;\\n      \\n      &lt;div class=\\&#34;content-str\\&#34;&gt;\\n         &lt;!--&amp;&amp; itemInfo.special_price_type != 5--&gt;\\n          &lt;span class=\\&#34;cur-price-wrap\\&#34;&gt;\\n            &lt;!-- \\n                &lt;span class=\\&#34;cur-price\\&#34;&gt;888&lt;/span&gt;\\n             --&gt;\\n            &lt;span class=\\&#34;cur-price\\&#34;&gt;888&lt;/span&gt;\\n            \\n          &lt;/span&gt;\\n          \\n        \\n        \\n        &lt;span class=\\&#34;item-name\\&#34;&gt;【新亮点品质】小辣椒X50Pro八核8+128G全网通4G安卓智能手机学生价百元游戏&lt;/span&gt;\\n        \\n        \\n        \\n        &lt;div class=\\&#34;item-wrap-bottom\\&#34;&gt;\\n        \\n        \\n        \\n          &lt;span class=\\&#34;deliver-money\\&#34;&gt;运费 ¥起&lt;/span&gt;\\n        \\n        &lt;/div&gt;\\n      &lt;/div&gt;\\n      &lt;div class=\\&#34;content-other\\&#34;&gt;\\n        &lt;span class=\\&#34;content-4\\&#34;&gt;&lt;/span&gt;\\n        &lt;span class=\\&#34;content-4\\&#34;&gt;&lt;/span&gt;\\n        &lt;span class=\\&#34;content-4\\&#34;&gt;&lt;/span&gt;\\n        &lt;span class=\\&#34;content-4\\&#34;&gt;&lt;/span&gt;\\n      &lt;/div&gt;\\n    &lt;/div&gt;\\n    &lt;div class=\\&#34;index-st-footer\\&#34;&gt;\\n      &lt;div class=\\&#34;footer-wrap-box\\&#34;&gt;\\n        &lt;span class=\\&#34;footer-str\\&#34;&gt;\\n          &lt;img class=\\&#34;footer-line-circle seller-logo\\&#34; src=\\&#34;https://si.geilicdn.com/h5user940874252-57f5000001767a079d410a20b7b9_400_400.jpg?w=70&amp;amp;h=70&amp;amp;cp=1\\&#34; alt=\\&#34;\\&#34;/&gt;\\n          &lt;span class=\\&#34;seller-content\\&#34;&gt;&lt;em class=\\&#34;seller-str\\&#34;&gt;联系店家&lt;/em&gt;&lt;/span&gt;\\n        &lt;/span&gt;\\n        &lt;span class=\\&#34;footer-str\\&#34;&gt;\\n          &lt;span class=\\&#34;footer-line-1 icon-shop\\&#34;&gt;&lt;/span&gt;\\n          &lt;span class=\\&#34;footer-line-2\\&#34;&gt;进店&lt;/span&gt;\\n        &lt;/span&gt;\\n        &lt;span class=\\&#34;footer-str\\&#34;&gt;\\n          &lt;span class=\\&#34;footer-line-1 icon-collect-empty\\&#34;&gt;&lt;/span&gt;\\n          &lt;span class=\\&#34;footer-line-2\\&#34;&gt;收藏&lt;/span&gt;\\n        &lt;/span&gt;\\n        &lt;span class=\\&#34;footer-btn\\&#34;&gt;\\n          \\n            &lt;span class=\\&#34;add-cart\\&#34;&gt;加入购物车&lt;/span&gt;\\n            &lt;span class=\\&#34;buy-now\\&#34;&gt;立即购买&lt;/span&gt;\\n          \\n        &lt;/span&gt;\\n      &lt;/div&gt;\\n    &lt;/div&gt;\\n    \\n  &lt;/div&gt;&#34;,&#34;preImg&#34;:&#34;&lt;link rel=\\&#34;preload\\&#34; as=\\&#34;image\\&#34; href=\\&#34;https://si.geilicdn.com/wdseller343240340-2cb800000175b75ac9c30a21348d-unadjust_800_800.jpeg?w=30&amp;h=30&amp;cp=1\\&#34;/&gt;&lt;link rel=\\&#34;preload\\&#34; as=\\&#34;image\\&#34; href=\\&#34;https://si.geilicdn.com/wdseller343240340-2cb800000175b75ac9c30a21348d-unadjust_800_800.jpeg?w=750&amp;h=750&amp;cp=1\\&#34;/&gt;&lt;link rel=\\&#34;preload\\&#34; as=\\&#34;image\\&#34; href=\\&#34;https://si.geilicdn.com/hz_img_047a00000162ae77f2a30a02685e_480_480_unadjust.png\\&#34;/&gt;&#34;,&#34;scriptHtml&#34;:&#34;&lt;script crossorigin=\\&#34;anonymous\\&#34; src=\\&#34;//assets.geilicdn.com/v-components/cpn-coupon-dialog/1.4.30/index.min.js\\&#34;&gt;&lt;/script&gt;&#34;,&#34;userDataRes&#34;:{&#34;status&#34;:{&#34;code&#34;:0,&#34;message&#34;:&#34;OK&#34;,&#34;description&#34;:&#34;&#34;},&#34;result&#34;:{&#34;top_banner&#34;:{&#34;block_success&#34;:true,&#34;buyer_level&#34;:0,&#34;buyer_point_num&#34;:0,&#34;cart_num&#34;:0,&#34;click_target&#34;:{&#34;order_url&#34;:&#34;https://i.weidian.com/index.php?umk=940874252&amp;wfr=c&amp;ifr=itemdetail&#34;,&#34;point_url&#34;:&#34;https://vmspub.weidian.com/gaia/9346/ac93327e.html?wfr=wdtopbar_sx&amp;spider_token=0fc2&amp;spider=seller.itemdetail.head.4&#34;,&#34;report_url&#34;:&#34;https://weidian.com/p5/shop/pages/report.php?userid=940874252&amp;itemID=4188765796&#34;,&#34;cart_url&#34;:&#34;https://weidian.com/cart/index.php?wfr=c&amp;ifr=itemdetail&#34;,&#34;reward_url&#34;:&#34;https://h5.weidian.com/m/rewarded/#/detail&#34;,&#34;custom_url&#34;:&#34;https://im.weidian.com/chat/?from_platform=h&amp;to_platform=a&amp;to_user_id=940874252&amp;im_enter=item&amp;im_item_id=4188765796&#34;},&#34;conf_info&#34;:{&#34;ad_info&#34;:{&#34;center_ad&#34;:{&#34;ad_desc&#34;:&#34;银行卡支付，笔笔享立减，最高99元！&#34;}},&#34;download_info&#34;:{&#34;render_desc&#34;:&#34;打开App，领最高888元立减红包&#34;,&#34;render_image&#34;:&#34;https://si.geilicdn.com/hz_poseidon_044f00000163010a810f0a02685e_72_72_unadjust.png&#34;,&#34;render_title&#34;:&#34;微店App&#34;}},&#34;user_logo&#34;:&#34;&#34;}}},&#34;exhibitSpaceRes&#34;:{&#34;666d8af358054c258260b990b36cbf8a&#34;:{&#34;spaceSort&#34;:0,&#34;dataList&#34;:[],&#34;renderType&#34;:null,&#34;spaceCode&#34;:&#34;666d8af358054c258260b990b36cbf8a&#34;},&#34;65a31093d964418c88b37c686c993788&#34;:{&#34;spaceSort&#34;:1,&#34;dataList&#34;:[],&#34;renderType&#34;:null,&#34;spaceCode&#34;:&#34;65a31093d964418c88b37c686c993788&#34;},&#34;739f973f88a342bd986227da1c5ab54e&#34;:{&#34;spaceSort&#34;:2,&#34;renderDesc&#34;:&#34;&#34;,&#34;relationId&#34;:19199,&#34;materialId&#34;:17507,&#34;exhibitId&#34;:469,&#34;spaceCode&#34;:&#34;739f973f88a342bd986227da1c5ab54e&#34;,&#34;spaceId&#34;:2017,&#34;features&#34;:&#34;{}&#34;,&#34;serverTime&#34;:1614069591261,&#34;startTime&#34;:1545148800000,&#34;endTime&#34;:1677623400000,&#34;renderType&#34;:&#34;coupon_common_template&#34;,&#34;spoor&#34;:&#34;1011.469.2017.17507.10&#34;,&#34;renderTitle&#34;:&#34;&#34;},&#34;bfa82961357942219c91b8d2653facdc&#34;:{&#34;spaceSort&#34;:3,&#34;dataList&#34;:[],&#34;renderType&#34;:null,&#34;spaceCode&#34;:&#34;bfa82961357942219c91b8d2653facdc&#34;},&#34;d1a8737182824ef4ab8a6d58991d3f8e&#34;:{&#34;spaceSort&#34;:4,&#34;renderDesc&#34;:&#34;&#34;,&#34;renderDataJson&#34;:{},&#34;relationId&#34;:19950,&#34;materialId&#34;:18257,&#34;exhibitId&#34;:469,&#34;spaceCode&#34;:&#34;d1a8737182824ef4ab8a6d58991d3f8e&#34;,&#34;spaceId&#34;:2134,&#34;features&#34;:&#34;{\\&#34;last_operator\\&#34;:\\&#34;zhaoxing\\&#34;,\\&#34;alarm\\&#34;:true}&#34;,&#34;serverTime&#34;:1614069591263,&#34;startTime&#34;:1546358400000,&#34;endTime&#34;:2019154800000,&#34;renderType&#34;:&#34;coupon_common_template&#34;,&#34;spoor&#34;:&#34;1011.469.2134.18257.10&#34;,&#34;renderTitle&#34;:&#34;1.4.30&#34;},&#34;5bb35c4ae7c34fd5a6b0f0b5b068e41e&#34;:{&#34;spaceSort&#34;:5,&#34;dataList&#34;:[{&#34;renderImage&#34;:null,&#34;renderDesc&#34;:&#34;&#34;,&#34;renderDataJson&#34;:{&#34;slogon&#34;:&#34;居然捡到了一个翻倍大红包&#34;},&#34;relationId&#34;:22791,&#34;exhibitId&#34;:469,&#34;materialId&#34;:21095,&#34;crowdId&#34;:10,&#34;spaceId&#34;:2385,&#34;features&#34;:&#34;{}&#34;,&#34;serverTime&#34;:1614069615044,&#34;startTime&#34;:1552550400000,&#34;endTime&#34;:1909882200000,&#34;renderType&#34;:&#34;double_red_packet_template&#34;,&#34;spoor&#34;:&#34;1011.469.2385.21095.10&#34;,&#34;renderTitle&#34;:&#34;&#34;,&#34;materialSortValue&#34;:0}],&#34;renderType&#34;:&#34;double_red_packet_template&#34;,&#34;spaceCode&#34;:&#34;5bb35c4ae7c34fd5a6b0f0b5b068e41e&#34;},&#34;05b9b13998b44cebaab530cc1fc7cf31&#34;:{&#34;spaceSort&#34;:6,&#34;dataList&#34;:[{&#34;renderImage&#34;:null,&#34;renderDesc&#34;:null,&#34;redirectUrl&#34;:null,&#34;renderDataJson&#34;:{&#34;right_corner_img&#34;:&#34;https://si.geilicdn.com/poseidon-7bf10000016b934d043e0a219248-unadjust_96_72.gif&#34;,&#34;center_img&#34;:&#34;https://si.geilicdn.com/poseidon-25b70000016c2cd800440a2166a4-unadjust_575_709.png&#34;,&#34;expose_times&#34;:&#34;10&#34;},&#34;relationId&#34;:28264,&#34;exhibitId&#34;:469,&#34;materialId&#34;:26526,&#34;crowdId&#34;:10,&#34;spaceId&#34;:2609,&#34;features&#34;:&#34;{\\&#34;last_operator\\&#34;:\\&#34;zhaoxing\\&#34;,\\&#34;openMaterialExperiment\\&#34;:true,\\&#34;alarm\\&#34;:false}&#34;,&#34;materialKey&#34;:&#34;dfa320f55d6743f99062a7fd854c0c7f&#34;,&#34;serverTime&#34;:1614069615044,&#34;startTime&#34;:1563937200000,&#34;endTime&#34;:1890748800000,&#34;renderType&#34;:&#34;detail_no_login_download_template&#34;,&#34;spoor&#34;:&#34;1011.469.2609.26526.10&#34;,&#34;renderTitle&#34;:&#34;shop&#34;,&#34;materialSortValue&#34;:0}],&#34;renderType&#34;:&#34;detail_no_login_download_template&#34;,&#34;spaceCode&#34;:&#34;05b9b13998b44cebaab530cc1fc7cf31&#34;}}}";
        source = source.replaceAll("&#34;", "\"");
        JSONObject jsonObject = JSONObject.parseObject(source);
        System.out.println(jsonObject);
    }

    /**
     * 字符串过滤
     *
     * @param source 源字符串
     * @param regex  表达式
     */
    public static String extractString(String source, String regex) {
        return extractString(source, regex, 0);
    }

    /**
     * 字符串过滤
     *
     * @param source 源字符串
     * @param regex  表达式
     */
    public static String extractString(String source, String regex, int group) {
        if (StringUtils.isAnyBlank(source, regex)) {
            return null;
        }
        Matcher matcher = PatternContainerUtil.getPattern(regex).matcher(source);
        String str = null;
        while (matcher.find()) {
            str = matcher.group(group);
        }
        return trim(str);
    }


    /**
     * 字符串匹配
     *
     * @param source 源字符串
     * @param regex  表达式
     */
    public static boolean isMatcher(String source, String regex) {
        if (StringUtils.isAnyBlank(source, regex)) {
            return false;
        }
        Matcher matcher = PatternContainerUtil.getPattern(regex).matcher(source.trim());
        return matcher.find();
    }

    /**
     * 字符串过滤并且替换
     *
     * @param source 源字符串
     * @param regex  表达式
     */
    public static String extractAndReplace(String source, String regex, String... target) {
        String str = extractString(source, regex, 0);
        if (isBlank(str)) {
            return null;
        }
        return replaces(str, target);
    }

    /**
     * 字符串过滤并且替换
     *
     * @param source 源字符串
     * @param regex  表达式
     */
    public static String extractAndReplace(String source, String regex, int group, String... target) {
        String str = extractString(source, regex, group);
        if (isBlank(str)) {
            return null;
        }
        return replaces(str, target);
    }

    /**
     * 替换
     *
     * @param source
     * @param target
     * @return
     */
    public static String replaces(String source, String... target) {
        if (isBlank(source)) {
            return null;
        }
        if (target.length == 0) {
            return source;
        }
        for (String t : target) {
            if (isEmpty(t)) {
                continue;
            }
            source = source.replace(t, StringUtils.EMPTY);
            if (isBlank(source)) {
                return null;
            }
        }
        return source;
    }

    /**
     * 字符串过滤并且替换
     *
     * @param source 源字符串
     * @param regex  表达式
     */
    public static String extractAndReplaceAll(String source, String regex, int group, String... target) {
        if (isBlank(source)) {
            return null;
        }
        String str = extractString(source, regex, group);
        if (isBlank(str)) {
            return null;
        }
        for (String s : target) {
            str = str.replaceAll(s, StringUtils.EMPTY);
            if (isBlank(str)) {
                return null;
            }
        }
        return str;
    }

    /**
     * 字符串转JSONObject
     *
     * @param source  源字符串
     * @param message 转换失败提示消息
     * @return
     */
    public static JSONObject convertJSONObject(String source, String message) {
        return convertJSONObject(source, message, false);
    }

    /**
     * 字符串转JSONObject
     *
     * @param source         源字符串
     * @param message        转换失败提示消息
     * @param throwException true抛异常，false不抛
     * @return
     */
    public static JSONObject convertJSONObject(String source, String message, boolean throwException) {
        if (source == null) {
            return null;
        }
        try {
            return JSONObject.parseObject(source);
        } catch (Exception e) {
            if (throwException) {
                throw e;
            }
            log.error("原值：{}, {}", source, message, e);
        }
        return null;
    }

    /**
     * 字符串转对象
     *
     * @param source  源字符串
     * @param clazz   对象类型
     * @param message 转换失败提示消息
     * @return
     */
    public static <T> T convertObject(String source, Class<T> clazz, String message) {
        return convertObject(source, clazz, message, false);
    }

    /**
     * 字符串转对象
     *
     * @param source         源字符串
     * @param clazz          对象类型
     * @param message        转换失败提示消息
     * @param throwException true抛异常，false不抛
     * @return
     */
    public static <T> T convertObject(String source, Class<T> clazz, String message, boolean throwException) {
        if (source == null) {
            return null;
        }
        try {
            return JSONObject.parseObject(source, clazz);
        } catch (Exception e) {
            if (throwException) {
                throw e;
            }
            log.error("原值：{}, {}", source, message, e);
        }
        return null;
    }

    /**
     * 编码转换
     *
     * @param source         源字符串
     * @param charset        源编码
     * @param convertCharset 转换编码
     * @return
     */
    public static String convertCharacterCoding(String source, String charset, String convertCharset) {
        if (isBlank(source)) {
            return null;
        }
        try {
            return new String(source.getBytes(charset), convertCharset);
        } catch (UnsupportedEncodingException e) {
            log.error("原值：{}, 字符串编码转换异常", source, e);
            return null;
        }
    }

    /**
     * 通过index获取对应下标的值。越界返回空字符串
     *
     * @param sources
     * @param index
     * @return
     */
    public static String getByIndex(String[] sources, int index) {
        if (sources == null || sources.length == 0) {
            return StringUtils.EMPTY;
        }
        if (sources.length > index) {
            return sources[index];
        }
        return StringUtils.EMPTY;
    }
}
