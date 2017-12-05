package com.stackroute.activitystream;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.activitystream.dao.UserDAO;
import com.stackroute.activitystream.model.User;

//see similar comments in circle controller.
@RestController
public class UserController {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	HttpSession session;
	
	Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user)
	{
		try
		{
			userDAO.createUser(user);
			logger.debug("Registration Sucessfull");
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		catch(Exception e)
		{
			logger.debug("Registration Unsucessfull");
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> authenticateUser(@RequestBody User user,HttpSession session)
	{
		User newUser=userDAO.authenticateUser(user);
		if(newUser != null)
		{
			session.setAttribute("userobject",newUser);
			return new ResponseEntity<User>(newUser,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.PUT)
	public ResponseEntity<?> logout()
	{
		User user=(User)session.getAttribute("userobject");
		if(user != null)
		{
			session.invalidate();
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value="/updateuser",method=RequestMethod.POST)
	public ResponseEntity<?> updateUser(@RequestBody User user)
	{
		try
		{
			userDAO.updateUser(user);
			logger.debug("Registration Sucessfull");
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		catch(Exception e)
		{
			logger.debug("Registration Unsucessfull");
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*@RequestMapping(value="/getuserbyuserid",method=RequestMethod.GET)
	public ResponseEntity<?> getUserByUserId(HttpSession session)
	{
		User newUser=(User)session.getAttribute("userobject");
		if(newUser != null)
		{
			newUser= userDAO.getUserByUserId(newUser.getUserId());
			return new ResponseEntity<User>(newUser,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}*/
	
	@RequestMapping(value="/getallusers",method=RequestMethod.GET)
	public ResponseEntity<?> getAllUsers()
	{
		List<User> allUsers=userDAO.getAllUsers();
		if(allUsers.isEmpty())
		{
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		else
		{
			return new ResponseEntity<List<User>>(allUsers,HttpStatus.OK);
		}
		
	}

}
