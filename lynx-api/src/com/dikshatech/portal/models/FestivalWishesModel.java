package com.dikshatech.portal.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.dikshatech.beans.FestivalWishesBean;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.common.utils.PortalUtility;
import com.dikshatech.portal.actions.ActionMethods;
import com.dikshatech.portal.actions.ActionResult;
import com.dikshatech.portal.dao.FestivalWishesDao;
import com.dikshatech.portal.dao.RegionsDao;
import com.dikshatech.portal.dto.FestivalWishes;
import com.dikshatech.portal.dto.FestivalWishesPk;
import com.dikshatech.portal.dto.Regions;
import com.dikshatech.portal.exceptions.DocumentMappingDaoException;
import com.dikshatech.portal.factory.DocumentMappingDaoFactory;
import com.dikshatech.portal.factory.FestivalWishesDaoFactory;
import com.dikshatech.portal.factory.RegionsDaoFactory;
import com.dikshatech.portal.forms.DropDown;
import com.dikshatech.portal.forms.PortalForm;

public class FestivalWishesModel extends ActionMethods {

	private static Logger	logger	= LoggerUtil.getLogger(FestivalWishesModel.class);

	@Override
	public ActionResult login(PortalForm form, HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult save(PortalForm form, HttpServletRequest request) {
		FestivalWishesDao festivalDao = FestivalWishesDaoFactory.create();
		ActionResult result = new ActionResult();
		try{
			FestivalWishesBean festivalWishsForm = (FestivalWishesBean) form;
			String[] festivalListUI;
			int regionIdUI = festivalWishsForm.getRegionId();
			int year = festivalWishsForm.getYear();
			try{
				FestivalWishes[] fs = festivalDao.findByDynamicWhere("REGION_ID=? AND YEAR=?", new Object[] { regionIdUI, year });
				if (fs != null && fs.length > 0){
					result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.failedtocreate"));
					return result;
				}
			} catch (Exception e){}
			festivalListUI = festivalWishsForm.getFestivalList();
			if (festivalListUI.length > 0 && regionIdUI > 0){
				for (String festival : festivalListUI){
					String[] festivalArr = festival.split("~=~");
					FestivalWishes festivalWishsDto = new FestivalWishes();
					for (String festivalArrItem : festivalArr){
						String[] festivalArrItemList = festivalArrItem.split("=");
						String key = festivalArrItemList[0];
						String value = festivalArrItemList[1];
						if (key.equals("dof")){
							festivalWishsDto.setDof(PortalUtility.StringToDate(value));
						} else if (key.equals("name")){
							festivalWishsDto.setName(value);
						} else if (key.equals("wishes")){
							festivalWishsDto.setWishes(value);
						} else if (key.equals("imageId")){
							try{
								festivalWishsDto.setImageId(Integer.parseInt(value));
							} catch (Exception e){}
						}
					}
					festivalWishsDto.setRegionId(regionIdUI);
					festivalWishsDto.setYear(year);
					festivalDao.insert(festivalWishsDto);
				}
			}
		} catch (Exception e){
			if (e.toString().contains("Duplicate")){
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.failedtocreate"));
			} else{
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.failedtosave"));
			}
		}
		return result;
	}

	@Override
	public ActionResult receive(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		DropDown dropDown = new DropDown();
		FestivalWishesDao festivalDao = FestivalWishesDaoFactory.create();
		RegionsDao resDao = RegionsDaoFactory.create();
		List<FestivalWishesBean> festivalBean = new ArrayList<FestivalWishesBean>();
		switch (ActionMethods.ReceiveTypes.getValue(form.getrType())) {
			case RECEIVEALL:
				try{
					Integer[] regionId = festivalDao.findByDistinct("SELECT DISTINCT REGION_ID FROM FESTIVAL_WISHES", "REGION_ID");
					for (Integer rId : regionId){
						Regions reg = resDao.findByPrimaryKey(rId);
						Integer[] years = festivalDao.findByDistinct("SELECT DISTINCT YEAR FROM FESTIVAL_WISHES WHERE REGION_ID=" + rId, "YEAR");
						for (Integer year : years){
							festivalBean.add(new FestivalWishesBean(rId, reg.getRegName(), year));
						}
					}
					dropDown.setDropDown(festivalBean.toArray());
				} catch (Exception e){
					e.printStackTrace();
				}
				request.setAttribute("actionForm", dropDown);
				break;
			case RECEIVE:
				FestivalWishesBean festivalWishsForm = (FestivalWishesBean) form;
				try{
					FestivalWishes festival[] = festivalDao.findByDynamicWhere("REGION_ID=? AND YEAR=?", new Object[] { festivalWishsForm.getRegionId(), festivalWishsForm.getYear() });
					festivalWishsForm.setFestivalWishes(festival);
					Regions reg = resDao.findByPrimaryKey(festivalWishsForm.getRegionId());
					if (reg != null) festivalWishsForm.setRegion(reg.getRegName());
				} catch (Exception e){
					// TODO: handle exception
				}
				request.setAttribute("actionForm", festivalWishsForm);
				break;
			default:
				break;
		}// switch ends
		return result;
	}

	@Override
	public ActionResult update(PortalForm form, HttpServletRequest request) {
		FestivalWishesDao festivalDao = FestivalWishesDaoFactory.create();
		ActionResult result = new ActionResult();
		try{
			FestivalWishesBean festivalWishsForm = (FestivalWishesBean) form;
			String[] festivalListUI;
			int regionIdUI = festivalWishsForm.getRegionId();
			int year = festivalWishsForm.getYear();
			List<Integer> listOfIds = new ArrayList<Integer>();
			List<Integer> todeleteImages = new ArrayList<Integer>();
			Integer[] ids = festivalDao.findByDistinct("SELECT DISTINCT ID FROM FESTIVAL_WISHES WHERE REGION_ID = " + regionIdUI + " AND YEAR = " + year, "ID");
			for (Integer id : ids)
				listOfIds.add(id);
			festivalListUI = festivalWishsForm.getFestivalList();
			if (festivalListUI.length > 0 && regionIdUI > 0){
				for (String festival : festivalListUI){
					String[] festivalArr = festival.split("~=~");
					FestivalWishes festivalWishsDto = new FestivalWishes();
					for (String festivalArrItem : festivalArr){
						String[] festivalArrItemList = festivalArrItem.split("=");
						String key = festivalArrItemList[0];
						String value = festivalArrItemList[1];
						if (key.equals("dof")){
							festivalWishsDto.setDof(PortalUtility.StringToDate(value));
						} else if (key.equals("name")){
							festivalWishsDto.setName(value);
						} else if (key.equals("wishes")){
							festivalWishsDto.setWishes(value);
						} else if (key.equals("imageId")){
							try{
								festivalWishsDto.setImageId(Integer.parseInt(value));
							} catch (Exception e){}
						} else if (key.equals("id")){
							try{
								festivalWishsDto.setId(Integer.parseInt(value));
								listOfIds.remove(Integer.valueOf(value));
							} catch (Exception e){}
						}
					}
					festivalWishsDto.setRegionId(regionIdUI);
					festivalWishsDto.setYear(year);
					if (festivalWishsDto.getId() == 0) festivalDao.insert(festivalWishsDto);
					else{
						FestivalWishes fest = festivalDao.findByPrimaryKey(festivalWishsDto.getId());
						if (fest.getImageId() != festivalWishsDto.getId()){
							todeleteImages.add(fest.getImageId());
						}
						festivalDao.update(new FestivalWishesPk(festivalWishsDto.getId()), festivalWishsDto);
					}
				}
				Integer[] imgaeIds = null;
				if (!listOfIds.isEmpty()){
					String wherecls = listOfIds.toString().replace("[", "(").replace("]", ")");
					imgaeIds = festivalDao.findByDistinct("SELECT IMAGE_ID FROM FESTIVAL_WISHES WHERE ID IN " + wherecls, "IMAGE_ID");
					festivalDao.delete(listOfIds);
				}
				if (imgaeIds != null && imgaeIds.length > 0) todeleteImages.addAll(Arrays.asList(imgaeIds));
				deleteImages(todeleteImages);
			}
		} catch (Exception e){
			if (e.toString().contains("Duplicate")){
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.failedtocreate"));
			} else{
				result.getActionMessages().add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.failedtosave"));
			}
		}
		return result;
	}

	private void deleteImages(List<Integer> todeleteImages) {
		if (todeleteImages == null || todeleteImages.isEmpty()) return;
		try{
			if (logger.isDebugEnabled()){
				logger.debug(" Deleting imgaes from DocumentMapping table " + todeleteImages);
			}
			DocumentMappingDaoFactory.create().update("DELETE FROM DOCUMENTS WHERE ID IN (SELECT DOCUMENT_ID FROM DOCUMENT_MAPPING WHERE ID IN " + (todeleteImages.toString().replace("[", "(").replace("]", ")")) + ")");
		} catch (DocumentMappingDaoException e){
			e.printStackTrace();
		}
	}

	@Override
	public ActionResult delete(PortalForm form, HttpServletRequest request) {
		ActionResult result = new ActionResult();
		try{
			FestivalWishesBean festivalWishsForm = (FestivalWishesBean) form;
			FestivalWishesDaoFactory.create().delete("DELETE FROM FESTIVAL_WISHES WHERE REGION_ID = " + festivalWishsForm.getRegionId() + " AND YEAR = " + festivalWishsForm.getYear());
		} catch (Exception e){}
		return result;
	}

	@Override
	public ActionResult validate(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult exec(PortalForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult defaultMethod(PortalForm form, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
}
