<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Main</title>
    <link rel="stylesheet" href="${app}/static/css/core-style.css">
</head>
<body>
<main>
    <div class="container">
        <section class="shop_grid_area mt-40">
            <div class="row">
                <div class="col-12 col-md-4 col-lg-3">
                    <div class="shop_sidebar_area">
                        <div class="widget range mb-50">
                            <p class="widget-title mb-30"><fmt:message key="elective.courses.name"/></p>
                            <div class="widget-desc">
                                <a href="${app}/admin/courses/filter?key=alphabet&value=AZ"> az </a>
                                <a href="${app}/admin/courses/filter?key=alphabet&value=ZA"> | za</a>
                            </div>
                        </div>
                        <div class="widget range mb-50">
                            <p class="widget-title mb-30"><fmt:message key="elective.courses.duration"/></p>
                            <div class="widget-desc">
                                <a href="${app}/admin/courses/filter?key=duration&value=MIN-MAX"> MIN-MAX </a>
                                <a href="${app}/admin/courses/filter?key=duration&value=MAX-MIN"> | MAX-MIN</a>
                            </div>
                        </div>
                        <div class="widget range mb-50">
                            <p class="widget-title mb-30"><fmt:message key="elective.courses.students"/></p>
                            <div class="widget-desc">
                                <a href="${app}/admin/courses/filter?key=students&value=MIN-MAX"> MIN-MAX </a>
                                <a href="${app}/admin/courses/filter?key=students&value=MAX-MIN"> | MAX-MIN</a>
                            </div>
                        </div>

                        <div class="widget price mb-50">
                            <p class="widget-title mb-30"><fmt:message key="elective.courses.teacher"/></p>
                            <div class="widget-desc">
                                <form action="${app}/admin/courses/filter" method="get">
                                    <input type="hidden" name="key" value="teacher_login">
                                    <select name="value" required class="form-check-input inputMinMax">
                                        <c:forEach items="${requestScope.teachers}" var="teacher">
                                            <option value="${teacher.getId()}">${teacher.getLogin()}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="submit" value="<fmt:message key="elective.courses.find.courses"/>"
                                           class="btnSubmitMainPage">
                                </form>
                            </div>
                        </div>
                        <div class="widget price mb-50">
                            <p class="widget-title mb-30"><fmt:message key="elective.courses.topic"/></p>
                            <div class="widget-desc">
                                <form action="${app}/admin/courses/filter" method="get">
                                    <input type="hidden" name="key" value="topic">
                                    <select name="value" required class="form-check-input inputMinMax">
                                        <c:forEach items="${requestScope.topics}" var="topic">
                                            <option value="${topic.getName()}">${topic.getName()}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="submit" value="<fmt:message key="elective.courses.find.courses"/>"
                                           class="btnSubmitMainPage">
                                </form>
                            </div>
                        </div>
                        <div class="widget price mb-50">
                            <p class="widget-title mb-30"><fmt:message key="elective.courses.start.date"/></p>
                            <div class="widget-desc">
                                <form action="${app}/admin/courses/filter" method="get">
                                    <input type="hidden" name="key" value="date_started">
                                    <input type="date" name="date_start" value="${requestScope.dateNow}"
                                           min="${requestScope.dateNow}"
                                           required class="form-check-input inputMinMax">
                                    <input type="submit" value="<fmt:message key="elective.courses.find.courses"/>"
                                           class="btnSubmitMainPage">
                                </form>
                            </div>
                        </div>
                    </div>
                    </form>
                </div>
                <div class="col-12 col-md-7 offset-md-1 col-lg-8 offset-lg-1">
                    <p><fmt:message key="elective.courses.sort"/></p>
                    <a href="${app}/admin/courses/filter?key=status&value=NOT_STARTED"> NOT STARTED </a>
                    <a href="${app}/admin/courses/filter?key=status&value=CURRENT"> | CURRENT</a>
                    <a href="${app}/admin/courses/filter?key=status&value=FINISHED"> | FINISHED</a>
                    <a href="${app}/elective/courses"> | ALL</a>
                    <br/>
                    <br/>
                    <form action="${app}/admin/courses/filter" method="get" class="form-product">
                        <input type="hidden" name="key" value="name">
                        <label for="productName">
                            <input type="text" minlength="2" name="value" id="productName"
                                   placeholder="<fmt:message key="elective.courses.name"/>" required>
                        </label>
                        <input type="submit" value="<fmt:message key="elective.courses.find.courses"/>"
                               class="btn-login">
                    </form>
                    <br/>

                    <c:if test="${not empty requestScope.message}">
                        <div class="message">
                            <h3><fmt:message key="elective.courses.message"/> ${requestScope.courseList.size()}
                                <fmt:message key="elective.courses.filter.courses.by"/> <fmt:message
                                        key="${requestScope.message}"/> ${requestScope.value}</h3>
                            <br/>
                        </div>
                    </c:if>

                    <!--                     list of products-->
                    <c:if test="${requestScope.courseList.size() > 0}">
                    <div class="row">
                        <c:forEach items="${requestScope.courseList}" var="course">
                            <div class="col-12 col-sm-6 col-lg-4 all_items">
                                <div class="single-product-wrapper">
                                    <div class="product-img">
                                        <a href="${app}/elective/separate/course?id=${course.getId()}">
                                            <img src="${app}/static/image/Course.jpg" alt="image">
                                        </a>
                                    </div>
                                    <div class="product-description d-flex align-items-center justify-content-between">
                                        <div class="product-meta-data">
                                            <div class="line"></div>
                                            <p class="product-price"> ${course.getName()} </p>
                                            <a href="${app}/elective/separate/course?id=${course.getId()}">
                                                <h6 class="course_date">
                                                    <mylib2:Dates courseDto="${course}"/>
                                                </h6>
                                            </a>
                                        </div>
                                        <div class="cart">
                                            <c:choose>
                                                <c:when test="${sessionScope.user.getRoleId() == 2 && course.getStatus() == 'NOT_STARTED'}">
                                                    <form action="${app}/elective/enrolle?id=${course.getId()}"
                                                          method="post">
                                                        <input type="image" name="submit"
                                                               title="<fmt:message key="elective.separate.course.enrolle"/>"
                                                               src="${app}/static/image/Plus.png" alt="submit">
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <form action="${app}/elective/separate/course" method="get">
                                                        <input type="hidden" name="id" value="${course.getId()}">
                                                        <input type="image" name="submit"
                                                               title="<fmt:message key="elective.account.see"/>"
                                                               src="${app}/static/image/Eye.png" alt="submit">
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="button__more">
                        <button id="btnMore"><fmt:message key="load.more"/></button>
                    </div>
                </div>
            </div>
        </section>
    </div>
    </c:if>
</main>
<script src="${app}/static/js/jquery/jquery-2.2.4.min.js"></script>
<script src="${app}/static/js/more.js"></script>
</body>
</html>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>