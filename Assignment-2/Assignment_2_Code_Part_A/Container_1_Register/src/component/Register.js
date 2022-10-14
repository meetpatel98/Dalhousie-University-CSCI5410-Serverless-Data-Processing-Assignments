import React, { useState } from "react";
import firestoreDatabase from "../firebaseConfiguration";
import { collection, addDoc, serverTimestamp } from "firebase/firestore";
import { Button } from "@material-ui/core";
import "../css/Register.css";

const Register = (callback) => {
  const [errors, setErrors] = useState({});
  const [data, updateData] = useState({
    name: "",
    email: "",
    location: "",
    password: "",
  });
  const onChange = (e) => {
    updateData({
      ...data,
      [e.target.name]: e.target.value,
    });
  };
  const addUserInFireStore = async () => {
    try {
      const userState = await addDoc(collection(firestoreDatabase, "users"), {
        email: data.email,
        timestamp: serverTimestamp(),
        status: "offline",
      });

      const userState2 = await addDoc(collection(firestoreDatabase, "users"), {
        email: data.email,
        password: data.password,
        location: data.location,
        name: data.name
      });

      console.log("Document written with ID: ", userState.id);
      console.log("Document written with ID: ", userState2.id);
      
      updateData({
        name: "",
        email: "",
        location: "",
        password: "",
      });
      window.location.href = "https://container-2-anmmfspxoa-ue.a.run.app/";
    } catch (e) {
      console.error("Error ", e);
    }
  };

  const onSubmit = (e) => {
    e.preventDefault();
    let errors = validate(data);
    setErrors(validate(data));
    console.log(Object.keys(errors).length);
    if (Object.keys(errors).length === 0) {
      addUserInFireStore();
    }
  };

  function validate(data) {
    let errors = {};

    if (!data.name.trim()) {
      errors.name = "Name required";
    }

    if (!data.location.trim()) {
      errors.location = "Location required";
    }

    if (!data.email) {
      errors.email = "Email required";
      // Regex Source link: https://regexr.com/3e48o
    } else if (!/^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/.test(data.email)) {
      errors.email = "Email address is invalid";
    }

    if (!data.password) {
      errors.password = "Password required";
    } else if (data.password.length < 8) {
      errors.password = "Password needs to be 8 characters or more";
    }
    return errors;
  }
  return (
    <div className="form-container">
      <div className="form-content-right">
        <form className="form" onSubmit={onSubmit}>
          <h1>Signup</h1>
          <div className="form-inputs">
            <input
              type="text"
              className="form-input"
              name="name"
              value={data.name}
              onChange={onChange}
              placeholder="Enter your name"
            />
            {errors.name && <p class="error">{errors.name}</p>}
          </div>
          <div className="form-inputs">
            <input
              type="text"
              className="form-input"
              name="email"
              value={data.email}
              onChange={onChange}
              placeholder="Enter you email"
            />
            {errors.email && <p class="error">{errors.email}</p>}
          </div>
          <div className="form-inputs">
            <input
              type="text"
              className="form-input"
              name="location"
              value={data.location}
              onChange={onChange}
              placeholder="Enter your location"
            />
            {errors.location && <p class="error">{errors.location}</p>}
          </div>
          <div className="form-inputs">
            <input
              type="password"
              className="form-input"
              name="password"
              value={data.password}
              onChange={onChange}
              placeholder="Enter password"
            />
            {errors.password && <p class="error">{errors.password}</p>}
          </div>
          <Button
            className="form-input-btn"
            color="secondary"
            variant="contained"
            type="submit"
          >
            Submit
          </Button>
        </form>
      </div>
    </div>
  );
};

export default Register;
