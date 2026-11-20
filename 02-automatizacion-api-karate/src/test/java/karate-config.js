function fn() {
  var env = karate.env || 'qa';
  karate.log('Ambiente de ejecución:', env);

  var config = {
    env: env,
    baseUrl: 'https://petstore.swagger.io/v2',
    rutaBase: 'classpath:co/com/sofka/petstore/'
  };

  // La PetStore pública es eventualmente consistente (varias réplicas detrás de un balanceador):
  // un GET inmediatamente posterior a un POST/PUT/DELETE puede no ver el cambio todavía.
  karate.configure('retry', { count: 10, interval: 1000 });
  karate.configure('connectTimeout', 10000);
  karate.configure('readTimeout', 15000);
  karate.configure('logPrettyRequest', true);
  karate.configure('logPrettyResponse', true);

  return config;
}
