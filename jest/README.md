# init jest
npm init -y
npm install --save-dev jest

# install babel
npm install --save-dev babel-jest @babel/core @babel/preset-env

# install json-server and mockjs
npm install -g json-server  
npm install mockjs --save-dev 


# install react and axios 
npm install react react-dom –save
npm install axios –save

npm install @babel/core @babel/preset-env @babel/preset-react @babel/plugin-syntax-jsx babel-loader –save-dev

.babelrc 
{
    presets: ['@babel/preset-env',
      ['@babel/preset-react', {runtime: 'automatic'}],
    ],
    "targets": {
        "node": "current"
    }
}

# run mock server
npm run mock

# run test
npm run test

# check test report


