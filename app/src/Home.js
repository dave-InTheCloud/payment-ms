import React, { useEffect, useState } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Card, CardGroup, CardHeader, Col, Container, ListGroup, ListGroupItem, Row } from 'reactstrap';
import { useCookies } from 'react-cookie';

const Home = () => {

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    fetch('/actuator/health') // <.>
      .then(response => response.text())
      .then(body => {

        setLoading(false);
      })
  }, [setLoading])



  const message = <h2>Welcome, to payment microservice example!</h2>;

  const button =
    <Row>
      <Col>
        <Button color="link"><Link to="/accounts">Manage Accounts</Link></Button>
        <Button color="link"><Link to="/customers">Manage Customers</Link></Button>
        <Button color="link"><Link to="/movements">Manage Movements</Link></Button>
        <br />
        <br />
        {/* <Button color="link" onClick={}>Logout</Button> */}
      </Col>
    </Row>

  const dockLinks =
    <Card
      style={{
        width: '18rem'
      }}
    >
      <CardHeader>
        Rest-API Documentation
      </CardHeader>
      <ListGroup flush>
        <ListGroupItem>
          <a href='/swagger-ui'>Swagger</a>
        </ListGroupItem>
        <ListGroupItem>
          <a href='/docs/index.html'>Asciidoctor</a>
        </ListGroupItem>
        <ListGroupItem>
          <a href='/docs/index.pdf'>Asciidoctor PDF</a>
        </ListGroupItem>
        <ListGroupItem>
          <a href='https://github.com/dave-InTheCloud/payment-ms'>GITHUB</a>
        </ListGroupItem>
      </ListGroup>
    </Card>

  const consoleLinks =
    <Card
      style={{
        width: '18rem'
      }}
    >
      <CardHeader>
        Web Console and monitoring
      </CardHeader>
      <ListGroup flush>
        <ListGroupItem>
          <a href="/actuator/health">Health actuator endpoint</a>
        </ListGroupItem>
        <ListGroupItem>
          <a href="/h2-console">Manage DB (H2 console password: spaceFan)</a>
        </ListGroupItem>
      </ListGroup>
    </Card>

  if (loading) {
    return <p>Loading...</p>;
  }

  return (
    <div>
      <AppNavbar />
      <Container fluid>
        {message}
        {button}
        <Row>
          <CardGroup>
            {dockLinks}
            {consoleLinks}
          </CardGroup>
        </Row>
      </Container>
    </div>
  );
}

export default Home;
