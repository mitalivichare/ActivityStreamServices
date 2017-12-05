package com.stackroute.activitystream;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.activitystream.dao.CircleDAO;
import com.stackroute.activitystream.model.Circle;

@RestController
public class CircleController {
	
	@Autowired
	CircleDAO circleDAO;
	
	/*@Autowired
	HttpSession session;*/
	
	
	@RequestMapping(value="/createcircle",method=RequestMethod.POST)
	public ResponseEntity<?> createCircle(@RequestBody Circle circle)
	{
		try
		{
		circleDAO.createCircle(circle);
		return new ResponseEntity<Circle>(circle,HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<Void>(HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@RequestMapping(value="/updatecircle",method=RequestMethod.POST)
	public ResponseEntity<?> updateCircle(@RequestBody Circle circle)
	{
		try
		{
			circleDAO.updateCircle(circle);
			return new ResponseEntity<Circle>(circle,HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/deletecircle/{circleId}",method=RequestMethod.GET)
	public ResponseEntity<?> deleteCircle(@PathVariable int circleId)
	{
		try
		{
			circleDAO.deleteCircle(circleId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/getallcircles",method=RequestMethod.GET)
	public ResponseEntity<?> getAllCircles()
	{
		try
		{
			List<Circle> circleList=circleDAO.getAllCircles();
			return new ResponseEntity<List<Circle>>(circleList,HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(value="/getcirclebycircleid/{circleId}",method=RequestMethod.GET)
	public ResponseEntity<?> getCircleByCircleId(@PathVariable int circleId)
	{
		try
		{
			Circle circle=circleDAO.getCircleByCircleId(circleId);
			return new ResponseEntity<Circle>(circle,HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

}
