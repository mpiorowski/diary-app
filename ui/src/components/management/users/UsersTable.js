import React, {Component, Fragment} from 'react';
import {Button, Divider, Icon, Input, Popconfirm, Table} from "antd";
import Highlighter from 'react-highlight-words';
import "./UsersTable.less";

class UsersTable extends Component {

  state = {
    searchText: '',
  };

  handleSearch = (selectedKeys, confirm) => {
    // confirm();
    this.setState({searchText: selectedKeys[0]});
  };

  handleReset = (clearFilters) => {
    clearFilters();
    this.setState({searchText: ''});
  };


  getColumnSearchProps = (dataIndex, placeholder) => ({
    filterDropdown: ({
                       setSelectedKeys, selectedKeys, confirm, clearFilters
                     }) => (
        <div style={{padding: 8}}>
          <Input
              ref={node => {
                this.searchInput = node;
              }}
              placeholder={`Search ${placeholder}`}
              value={selectedKeys[0]}
              onChange={e => setSelectedKeys(e.target.value ? [e.target.value] : [])}
              onPressEnter={() => this.handleSearch(selectedKeys, confirm)}
              style={{width: 188, marginBottom: 8, display: 'block'}}
          />
          <Button
              type="primary"
              onClick={() => this.handleSearch(selectedKeys, confirm)}
              icon="search"
              size="small"
              style={{width: 90, marginRight: 8}}
          >
            Search
          </Button>
          <Button
              onClick={() => this.handleReset(clearFilters)}
              size="small"
              style={{width: 90}}
          >
            Reset
          </Button>
        </div>
    ),
    filterIcon: filtered => <Icon type="search" style={{color: filtered ? '#1890ff' : undefined}}/>,
    onFilter: (value, record) => record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => this.searchInput.select());
      }
    },
    render: (text) => (
        <Highlighter
            highlightStyle={{backgroundColor: '#ffc069', padding: 0}}
            searchWords={[this.state.searchText]}
            autoEscape
            textToHighlight={text.toString()}
        />
    ),
  });

  handleSort = (a, b, key) => {
    return a[key].localeCompare(b[key]);
  };

  render() {

    const {tableData, tableLoading, usersRoles} = this.props;

    const columns = [{
      title: 'User Name',
      dataIndex: 'userName',
      key: 'userName',
      width: '30%',
      ...this.getColumnSearchProps('userName', 'name'),
      defaultSortOrder: 'ascend',
      sorter: (a, b) => this.handleSort(a, b, 'userName'),
    }, {
      title: 'User Email',
      dataIndex: 'userEmail',
      key: 'userEmail',
      width: '30%',
      ...this.getColumnSearchProps('userEmail', 'email'),
      sorter: (a, b) => this.handleSort(a, b, 'userEmail'),
    }, {
      title: 'User Role',
      dataIndex: 'userRole',
      key: 'userRole',
      filters: usersRoles.map(role => {
        return (
            {text: role["roleDesc"], value: role["roleDesc"]}
        )
      }),
      onFilter: (value, record) => record.userRole.includes(value),
      sorter: (a, b) => this.handleSort(a, b, 'userRole'),
    }, {
      title: 'Action', dataIndex: '', key: 'x',
      render: (text, record) => (
          <Fragment>
            <button className={"global-btn-link"}
                    onClick={() => this.props.handleModalVisible(true, record)}
            >Edit
            </button>
            <Divider type="vertical"/>
            <Popconfirm title="Sure to delete?" onConfirm={() => this.props.handleDeleteUser(record.key)}>
              <button className={"global-btn-link"}>Delete</button>
            </Popconfirm>
          </Fragment>
      )
    },
    ];

    return (
        <Table
            columns={columns}
            dataSource={tableData}
            size={"middle"}
            loading={tableLoading}
        />
    );
  }
}

export default UsersTable;