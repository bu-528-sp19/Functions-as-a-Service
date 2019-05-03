import { SET_CURRENT_POS, SET_DRIVER_POS } from './types';
import axios from 'axios';
import https from 'https';

process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";

const apiAddress = "https://openwhisk.faas.compulty.com:31001/api/v1/web/guest/default/searchDriver"

// set the backend api address

const instance = axios.create({
  httpsAgent: new https.Agent({  
    rejectUnauthorized: false
  })
});

// set the default postion
export const setDefaultPosition = () => dispatch => {
  const defaultPos = {
    center: {
      lat: 42.346398,
      lng: 288.903679
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
  const query_data = {
    passenger_id: "P123S2",
    latitude: data.yourCoord.lat,
    longitude: data.yourCoord.lng - 360
  }

  instance
    .post(apiAddress, query_data)
    .then(res => {
      let drivers = [];
      res.data.drivers.forEach(driver => {
        const driverInfo = {
          id: driver.id,
          lat: Number(driver.latitude),
          lng: 360 + Number(driver.longitude)
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