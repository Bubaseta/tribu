package com.mayorista.nadir.service;



/*@Service
@RequiredArgsConstructor
public class UsuarioEntityService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    @Override  
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         
        try {
             Usuario userEntity = usuarioRepositorio.findByUsername(username)
            .orElseThrow(() -> new UserPrincipalNotFoundException("El usuario " + username +" No existe. !"));
          
            Collection<? extends GrantedAuthority> authorities = userEntity.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())))
            .collect(Collectors.toSet());

            return new User(userEntity.getUsername(),
            userEntity.getPassword(),
            true,
            true,
            true,
            true,authorities);

        } catch (UserPrincipalNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

} */
