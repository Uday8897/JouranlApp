import React from "react";
import SwaggerUI from "swagger-ui-react";
import "swagger-ui-react/swagger-ui.css";

const Swagger = () => {
  return (
    <div>
      <h2>API Documentation</h2>
      <SwaggerUI url="http://localhost:8081/v3/api-docs" />
    </div>
  );
};

export default Swagger;
