import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Col, Container, Input, Label, Pagination, PaginationItem, PaginationLink, Row, Table, Tooltip } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { useCookies } from 'react-cookie';

const AccountList = () => {

  const [accounts, setAccounts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [tooltipOpen, setTooltipOpen] = useState(false);

  const [pageSize, setPageSize] = useState(5);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPage, setTotalPage] = useState(1);

  const toggle = () => setTooltipOpen(!tooltipOpen);

  useEffect(() => {
    setLoading(true);

    fetch('api/accounts')
      .then(response => response.json())
      .then(data => {
        setAccounts(data);
        setLoading(false);
      })
  }, []);

  const remove = async (id) => {
    await fetch(`/api/group/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      credentials: 'include'
    }).then(() => {
      let updatedAccounts = [...accounts].filter(i => i.id !== id);
      setAccounts(updatedAccounts);
    });
  }

  const changePage = (e, i) => {
    setCurrentPage(i)
  }


  const handlePageSizeChange = (event) => {
    const newPageSize = parseInt(event.target.value); // Convert selected value to number
    setPageSize(newPageSize);
    // Trigger data fetch or other actions based on new page size (commented out)
  };



  if (loading) {
    return <p>Loading...</p>;
  }


  const accountList = accounts.map(account => {

    //const address = `${account.address || ''} ${account.city || ''} ${account.stateOrProvince || ''}`;
    return <tr key={account.id}>
      <td style={{ whiteSpace: 'nowrap' }}>{account.id}</td>
      <td style={{ whiteSpace: 'nowrap' }}>{account.name}</td>
      <td>{account.serialNumber}</td>
      <td>{account.type}</td>
      <td>{account.currencyCode}</td>
      <td>{account.balance}</td>
      {/* <td>{account.events.map(event => {
        return <div key={event.id}>{new Intl.DateTimeFormat('en-US', {
          year: 'numeric',
          month: 'long',
          day: '2-digit'
        }).format(new Date(event.date))}: {event.title}</div>
      })}</td> */}
      <td>
        <ButtonGroup id='action'>
          <Button size="sm" color="primary" disabled tag={Link} to={"/accounts/" + account.id}>Edit</Button>
          <Button size="sm" color="danger" disabled onClick={() => remove(account.id)}>Delete</Button>
        </ButtonGroup>

        <Tooltip
          placement={'left'}
          isOpen={tooltipOpen}
          target={'action'}
          toggle={toggle}
        > Not yet implemented</Tooltip>
      </td>
    </tr>
  });

  return (

    <div>
      <AppNavbar />
      <Container fluid>
        <div className="float-end">
          <Button color="success" tag={Link} to="/accounts/new">Add account</Button>
        </div>
        <h3>ACCOUNTS</h3>
        <Table className="mt-4">
          <thead>
            <tr>
              <th>id</th>
              <th>name</th>
              <th>serialNumber</th>
              <th>Type</th>
              <th>Currency</th>
              <th>Balance</th>
              <th width="10%">Actions</th>
            </tr>
          </thead>
          <tbody>
            {accountList}
          </tbody>
        </Table>

        <div className="pagination-wrapper">

          <Row>
            <Col xs="1">
              <Label for="examplePassword">
                Page size
              </Label>
              <Input
                id="exampleSelect"
                name="select"
                type="select"
                value={pageSize}
                onChange={handlePageSizeChange}>
                <option value="5">5</option>
                <option value="10">10</option>
                <option value="20">20</option>
              </Input>
            </Col>
            <Col xs="11" md="11" className="d-flex align-items-center justify-content-center">

              <Pagination aria-label="Page navigation example">

                <PaginationItem disabled={currentPage <= 0}>

                  <PaginationLink
                    onClick={e => changePage(e, currentPage - 1)}
                    previous
                  />

                </PaginationItem>

                {loading ? (
                  <p>Loading pagination...</p>
                ) : (
                  [...Array(totalPage)].map((page, i) =>
                    <PaginationItem active={i === currentPage} key={i}>
                      <PaginationLink onClick={e => changePage(e, i)}>
                        {i + 1}
                      </PaginationLink>
                    </PaginationItem>
                  )
                )}

                <PaginationItem disabled={currentPage >= totalPage - 1}>

                  <PaginationLink
                    onClick={e => changePage(e, currentPage + 1)}
                    next
                  />

                </PaginationItem>

              </Pagination>

            </Col>
          </Row>

        </div>
      </Container>
    </div>
  );
};

export default AccountList;
