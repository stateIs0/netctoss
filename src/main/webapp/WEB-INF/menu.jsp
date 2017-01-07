<%@page pageEncoding="utf-8"%>
  <ul id="menu">
  		<!--这个可能是任意一个.do页面.所以是不知道当前路径是谁,写相对路径是不对的.应该写绝对路径.  -->
  		<!--此处适合写绝对路径原因如下:
  								1.假设当前浏览器打开了资费查询页面,用户点击主页要跳转过去,此时对于浏览器而言,
  								      当前路径是findCost.do,目标路径是toIndex.do
  								2.对于浏览器而言,当前路径和目标路径都*.do.
  								3.在写某链接时,一般目标路径是可以确定的,但当前路径是无法确定的,可能是任何页面.
  								4.此时只能写绝对路径.  -->
      <li><a href="/netctoss_cxs/toIndex.do" class="index_off"></a></li>
      <li><a href="" class="role_off"></a></li>
      <li><a href="" class="admin_off"></a></li>
      <li><a href="/netctoss_cxs/findCost.do" class="fee_off"></a></li>
      <li><a href="" class="account_off"></a></li>
      <li><a href="" class="service_off"></a></li>
      <li><a href="" class="bill_off"></a></li>
      <li><a href="" class="report_off"></a></li>
      <li><a href="" class="information_off"></a></li>
      <li><a href="" class="password_off"></a></li>
  </ul> 