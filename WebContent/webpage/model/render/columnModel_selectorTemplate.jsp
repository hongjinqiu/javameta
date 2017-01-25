<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<c:set value="" var="selectionTitle"></c:set>
<c:forEach items="${formTemplate.toolbarOrDataProviderOrColumnModel}" var="tmpItem">
	<c:if test="${item.xmlName == 'column-model'}">
		<c:set value="${item.selectionTitle}" var="selectionTitle"></c:set>
	</c:if>
</c:forEach>
<table class="selectionArea" cellspacing="0" cellpadding="0" border="0" width="100%">
			<tr>
				<td class="selectionAreaTd selectorLeftWrapper" valign="top">
					<div class="selectorLeftArea">
						<div class="selectionTitle">${selectionTitle}</div>
						<div class="selectionTitle2">选择内容</div>
						<div id="selectionResult">
							<!-- <div class="selectionItem">
								<div class="left">wwwwwwwww,选择的内容</div>
								<div class="right"><input type="hidden" name="selectionId" value="" /></div>
							</div>
							<div class="selectionItem">
								<div class="left">code,name</div>
								<div class="right" onclick="removeSelection(this)"><input type="hidden" name="selectionId" value="id" /></div>
							</div>
							 -->
						</div>
					</div>
				</td>
				<td class="selectionAreaTd selectorMiddleWrapper"  valign="top">
					<div class="selectorMiddleArea"></div>
				</td>
				<td class="selectionAreaTd selectorRightWrapper"  valign="top">
					<div style="margin-top: 10px;">
						<table id="${columnModelForJsp.name}"></table>
					</div>
					<script type="text/javascript">
					g_delayLi.push(function() {
						createSelectorTemplateGrid();
					});
					</script>
				</td>
			</tr>
		</table>
		<div style="margin-top: 5px">
			<input type="button" id="confirmBtn" value="确定" class="queryBtn" />
			<input type="button" id="clearBtn" value="清除" class="queryReset" />
		</div>
