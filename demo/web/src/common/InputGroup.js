import React from 'react'
import classnames from 'classnames';
import PropTypes from 'prop-types';

const InputGroup = ({
  name,
  placeholder,
  value,
  error,
  prepend,
  onChange
}) => {
  return (
    <div className="input-group mb-3">
        <div className="input-group-prepend">
            <span className="input-group-text">
                { prepend }
            </span>
        </div>
      <input 
      className={classnames('form-control form-control-lg', {
        'is-invalid': error
      })}
      placeholder={placeholder}
      value={value} 
      onChange={onChange}
      name={name} 
      />
      {error && (<div className="invalid-feedback">{error}</div>)}
    </div>
  );
};

InputGroup.propTypes = {
  name: PropTypes.string.isRequired,
  placeholder: PropTypes.string,
  value: PropTypes.string.isRequired,
  icon: PropTypes.string,
  error: PropTypes.string,
  onChange: PropTypes.func.isRequired
}

InputGroup.defaultProps = {
  type: 'text'
}

export default InputGroup;