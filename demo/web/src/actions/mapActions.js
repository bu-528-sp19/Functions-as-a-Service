import { SET_CURRENT_POS } from './types';

// set the default postion
export const setDefaultPosition = () => dispatch => {
  const defaultPos = {
    center: {
      lat: 42.3401,
      lng: 288.9089
    },
    zoom: 14
  }
  dispatch({
    type: SET_CURRENT_POS,
    payload: defaultPos
  })
}

// set the position
export const setPosition = (data) => dispatch => {
  dispatch({
    type: SET_CURRENT_POS,
    payload: data
  });
};