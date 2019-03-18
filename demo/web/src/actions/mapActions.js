import { SET_CURRENT_POS, SET_DRIVER_POS } from './types';
import axios from 'axios';

// set the backend api address
const apiAddress = "";

// set the default postion
export const setDefaultPosition = () => dispatch => {
  const defaultPos = {
    center: {
      lat: 42.3401,
      lng: 288.9089
    },
    zoom: 14,
    yourCoord: {},
    taxiCoord: []
  }
  dispatch({
    type: SET_CURRENT_POS,
    payload: defaultPos
  })
}

// set the position
export const setPosition = (data) => dispatch => {
  
  // query to the backend for the positions of taxi drivers around
  axios
    .post(apiAddress)
    .then(res => {
      let drivers = [];
      res.drivers.forEach(driver => {
        const driverInfo = {
          id: driver.id,
          lat: driver.latitude,
          lng: driver.longtitude
        };
        drivers.push(driverInfo);
      });
      dispatch({
        type: SET_DRIVER_POS,
        payload: drivers
      });
    })
    .catch(err => {
      console.log(err);
    });

  dispatch({
    type: SET_CURRENT_POS,
    payload: data
  });
};