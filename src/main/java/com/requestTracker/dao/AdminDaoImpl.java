package com.requestTracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.requestTracker.domain.Application;
import com.requestTracker.domain.Login;
import com.requestTracker.domain.Partner;
import com.requestTracker.domain.User;
import com.requestTracker.mapper.PartnerListMapper;
import com.requestTracker.mapper.PartnerUserMapper;
import com.requestTracker.mapper.AppIpListMapper;
import com.requestTracker.mapper.PartnerAppsMapper;

@Repository

public class AdminDaoImpl implements IAdminDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Partner> getAllPartner() {
		List<Partner> partnerList = new ArrayList<Partner>();
		String getAllPartner = "SELECT p.partner_name,p.partner_id,s.title FROM partner p , statusinfo s WHERE s.status_id=p.status_id ORDER BY p.partner_name ASC ";
		String getAppCount = "SELECT COUNT(app_id) AS app_number FROM partner_app WHERE partner_id = ? and status_id=1";
		String getUserCount = "SELECT  COUNT(user_id) AS user_number FROM userdetail WHERE partner_id =? and status_id=1";
		partnerList = jdbcTemplate.query(getAllPartner, new Object[] {}, new PartnerListMapper());

		if (partnerList.size() > 0) {
			for (int i = 0; i < partnerList.size(); i++) {
				int appCount = jdbcTemplate.queryForObject(getAppCount,
						new Object[] { partnerList.get(i).getPartnerId() }, Integer.class);
				int userCount = jdbcTemplate.queryForObject(getUserCount,
						new Object[] { partnerList.get(i).getPartnerId() }, Integer.class);
				partnerList.get(i).setAppCount(appCount);
				partnerList.get(i).setUserCount(userCount);
			}
			return partnerList;
		}

		return null;
	}

	public String addPartnerUser(User user, Login login) {
		String sqluserDetails = "INSERT INTO userdetail (partner_id, first_name, last_name,user_email,role )"
				+ " VALUES (?, ?, ?, ?, ?)";
		if (jdbcTemplate.update(sqluserDetails, new Object[] { user.getPartnerId(), user.getFname(), user.getLname(),
				user.getUserEmail(), user.getRole() }) > 0) {
			String sqllogin = "INSERT INTO login (email, password)" + " VALUES (?, ?)";
			if (jdbcTemplate.update(sqllogin, new Object[] { login.getEmail(), login.getPassword() }) > 0)
				return login.getPassword();
		}
		return null;
	}

	public List<User> getUserList(int pid) {
		String getAllUsers = "SELECT usr.user_id,usr.partner_id,usr.first_name,usr.last_name,usr.user_email,usr.role,usr.status_id FROM userdetail usr,statusinfo s, partner p WHERE s.status_id = usr.status_id AND usr.partner_id = p.partner_id AND p.partner_id = ?;";
		List<User> userList = jdbcTemplate.query(getAllUsers, new Object[] { pid }, new PartnerUserMapper());
		return userList;
	}

	public boolean updateStatus(User user, int viewStatusId) {
		String selectSql = "SELECT count(user_id) from  userdetail  where user_id=? and status_id=? ";
		int status_id = jdbcTemplate.queryForObject(selectSql, new Object[] { user.getUserId(), viewStatusId },
				Integer.class);
		if (status_id > 0) {
			String updateStatus = "UPDATE userdetail SET status_id = ? , updation_time =current_timestamp() WHERE user_id = ?";
			jdbcTemplate.update(updateStatus, new Object[] { user.getStatusId(), user.getUserId() });
			return true;
		}
		return false;
	}

	public boolean updateUserInfo(User user) {
		String updateUserInfo = "UPDATE userdetail SET first_name = ?,last_name=? , updation_time = current_timestamp() WHERE user_id = ?";
		if (jdbcTemplate.update(updateUserInfo,
				new Object[] { user.getFname(), user.getLname(), user.getUserId() }) > 0) {
			return true;
		}
		return false;
	}

	public List<Partner> getAppIpMap(int PartnerId) {
		List<Partner> appIpList = new ArrayList<Partner>();
		String getAppIpMap=" SELECT am.app_name, aki.ip, s.title "
				+ " FROM app_key_ip_mapping aki,partner_app pa,partner p,app_master am,statusinfo s "
				+ " WHERE aki.app_key = pa.app_key "
				+ " AND pa.app_id = am.app_id "
				+ " AND pa.partner_id = p.partner_id "
				+ " AND p.partner_id = ? "
				+ " AND pa.status_id = 1 "
				+ " AND aki.status_id = s.status_id AND aki.status_id =1 ";

		appIpList=jdbcTemplate.query(getAppIpMap,new Object[]{PartnerId}, new AppIpListMapper() );
		if(appIpList.size()>0){

			return appIpList;
		}
		return null;
	}
	

	public boolean setPartnerStatus(Partner partner) {
		String searchPartnerStatus = " SELECT count(partner_id) FROM partner where partner_id=? and status_id=? ";
		String updatePartnerStatus = " UPDATE partner SET updation_time=current_timestamp(), status_id = ? WHERE partner_id = ? ";
		int appCount = jdbcTemplate.queryForObject(searchPartnerStatus,
				new Object[] { partner.getPartnerId(), partner.getStatusId() }, Integer.class);
		if (appCount == 0) {
			jdbcTemplate.update(updatePartnerStatus, new Object[] { partner.getStatusId(), partner.getPartnerId() });
			return true;
		}
		return false;
	}

	public List<Partner> getPartnerApps(int pid) {
		String getPartnerapp = "SELECT distinct  am.app_id, p.partner_id, am.app_name, aki.ip, pa.app_key "
				+ " FROM partner p, app_master am, partner_app pa left join app_key_ip_mapping aki on aki.app_key = pa.app_key "
				+ " AND aki.status_id = 1 "
				+ " WHERE pa.app_id = am.app_id "
				+ " AND pa.partner_id = p.partner_id "
				+ " AND p.partner_id = ? "
				+ " AND  pa.status_id = 1 "
				+ " AND am.status_id = 1";
		return jdbcTemplate.query(getPartnerapp, new Object[] { pid }, new PartnerAppsMapper());
	}

	public String mapAppIps(List<Application> editAppList) {
		String updatePartnerApps = " UPDATE app_key_ip_mapping akim, "
				+ " partner_app pa SET akim.status_id = 2, akim.update_time=current_timestamp() WHERE pa.app_key = akim.app_key "
				+ " AND pa.partner_id = " + editAppList.get(0).getPartnerId();
						jdbcTemplate.update(updatePartnerApps);
			
			String editPartnerAps = "INSERT INTO app_key_ip_mapping(ip,app_key) VALUES (?,?)";
			for (int i = 0; i < editAppList.size(); i++) {
				System.out.println("In DAO+" + editAppList.get(i).getPartnerId() + "\t" + editAppList.get(i).getAppId()
						+ "\t" + editAppList.get(i).getAppIps() + "" + editAppList.get(i).getAppKey());
			}
			Iterator<String> ipiterator;
			
			for (int i = 0; i < editAppList.size(); i++) {
				System.out.println("In DAO :" +editAppList.get(i).getPartnerId() + "\t" + editAppList.get(i).getAppId() + "\t"
						+ editAppList.get(i).getAppIps() + "" + editAppList.get(i).getAppKey());
				ipiterator= editAppList.get(i).getAppIps().iterator();
				while(ipiterator.hasNext()){
					String ips=ipiterator.next();
					System.out.println("Updated Successfully" + jdbcTemplate.update(editPartnerAps,
							new Object[] { ips, editAppList.get(i).getAppKey() }));
				}
			}
			return "Update Successfully";
	}

	public int createNewApplication(final Application application) {
		int appId	=	0;
		final String addAppQuery	=	"INSERT INTO app_master (app_name,app_url ) VALUES (?,?)";
		final PreparedStatementCreator psc = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				final PreparedStatement ps = con.prepareStatement(addAppQuery, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, application.getApp_Name());
				ps.setString(2, application.getAppURL());
				
				return ps;
			}

			
		};
		final KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, holder);
		appId = holder.getKey().intValue();
		return appId;
		
	}

	public void changePassword(User user) {
		String updateUserInfo = "UPDATE login SET password=? , updated_time=current_timestamp() WHERE email= ?";
		jdbcTemplate.update(updateUserInfo,new Object[] { user.getPassword(),user.getUserEmail() });
		
	}
	
	public boolean checkEmail(User user){
		String emailCheck="SELECT count(user_email) FROM userdetail where user_email = ?";
		int emailExist=jdbcTemplate.queryForObject(emailCheck, new Object[] { user.getUserEmail() }, Integer.class);
		if(emailExist==0 ){
			return false;
		}
		return true;
		
	}

}
