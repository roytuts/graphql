import React from 'react';
import gql from 'graphql-tag'
import { ApolloClient } from 'apollo-client';
import { HttpLink } from 'apollo-link-http';
import { Query, Mutation, ApolloProvider } from 'react-apollo'
import { InMemoryCache } from 'apollo-cache-inmemory';

const cache = new InMemoryCache();

const link = new HttpLink({
	uri: 'http://localhost:8080/graphql'
});

const client = new ApolloClient({
	link,
	cache
});

const CATEGORIES_QUERY = gql`
  {
    allCategories {
      id
      name
    }
  }
`

const CATEGORY_CREATE_QUERY = gql`
  mutation addCategory($name: String!) {
    addCategory(name: $name){
	  id,
	  name
	}
  }
`

const CATEGORY_UPDATE_QUERY = gql`
  mutation UpdateCategory($cId:ID!, $name: String!) {
    updateCategory(id: $cId, name: $name){
	  id,
	  name
	}
  }
`

const CATEGORY_DELETE_QUERY = gql`
  mutation DeleteCategory($cId:ID!) {
    deleteCategory(id: $cId)
  }
`

class App extends React.Component {
	constructor(props) {
		super(props);
		this.headers = [
			{ key: 'id', label: 'Id'},
			{ key: 'name', label: 'Name' }
		];
	}
		
	render() {		
		return (
			<ApolloProvider client={client}>
				<Query query={CATEGORIES_QUERY}>
					{
						({ loading, error, data }) => {
							if (loading) return <div>Loading...</div>
							if (error) return <div>Error</div>
			
							const categories = data.allCategories						
			
							return (
								<table>
									<thead>
										<tr>
											{
												this.headers.map(function(h) {
													return (
														<th key = {h.key}>{h.label}</th>
													)
												})
											}
										</tr>
									</thead>
									<tbody>
										{										
											categories.map(function(c, key) {
												return (
													<tr key = {key}>
													  <td>{c.id}</td>
													  <td>{c.name}</td>
													</tr>
												);
											})										
										}
									</tbody>
								</table>
							);
						}
					}
				</Query>
				<Mutation mutation={CATEGORY_CREATE_QUERY}>
					{
						(addCategory, { data, loading, error }) => (
							<div>
								<button onClick={ () => {
									addCategory({ variables: { name: 'Electronics' } });
									}}>
									Add Category
								</button>
								{
									loading &&
									<div>adding category…</div>
								}
								{ 
									data &&
									<div>category added</div>
								}
								{
									error &&
									<div>Error adding category…</div>
								}
							</div>
						)
					}
				</Mutation>
				<Mutation mutation={CATEGORY_UPDATE_QUERY}>
					{
						(updateCategory, { data, loading, error }) => (
							<div>
								<button onClick={ () => {
									updateCategory({ variables: {cId: 28, name: 'Redme' } });
									}}>
									Update Category
								</button>
								{
									loading &&
									<div>updating category…</div>
								}
								{ 
									data &&
									<div>category updated</div>
								}
								{
									error &&
									<div>Error updating category…</div>
								}
							</div>
						)
					}
				</Mutation>
				<Mutation mutation={CATEGORY_DELETE_QUERY}>
					{
						(deleteCategory, { data, loading, error }) => (
							<div>
								<button onClick={ () => {
									deleteCategory({ variables: {cId: 28} });
									}}>
									Delete Category
								</button>
								{
									loading &&
									<div>deleting category…</div>
								}
								{ 
									data &&
									<div>category deleted</div>
								}
								{
									error &&
									<div>Error deleting category…</div>
								}
							</div>
						)
					}
				</Mutation>
			</ApolloProvider>
		);
	}
}

export default App;